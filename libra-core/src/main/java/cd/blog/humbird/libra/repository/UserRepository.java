package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_ENV_USED;
import static cd.blog.humbird.libra.helper.StatusHelper.IS_USER_USED;

/**
 * Created by david on 2018/7/16.
 */
@Repository
public class UserRepository {

    private static final String CACHE_USER_ = "cache_user_";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "caffeineClusterCache")
    private Cache cache;

    public List<User> findAll() {
        List<User> users = cache.get(CACHE_USER_ + "list", List.class);
        if (!CollectionUtils.isEmpty(users)) {
            return users;
        }
        synchronized (this) {
            users = cache.get(CACHE_USER_ + "list", List.class);
            if (CollectionUtils.isEmpty(users)) {
                users = userMapper.findAll();
                cache.put(CACHE_USER_ + "list", users);
            }
        }
        return users;
    }

    public User findById(long id) {
        User user = cache.get(CACHE_USER_ + id, User.class);
        if (user != null) {
            return user;
        }
        user = userMapper.findById(id);
        if (user != null) {
            cache.put(CACHE_USER_ + id, user);
        }
        return user;
    }

    public User findByName(String name) {
        User user = cache.get(CACHE_USER_ + name, User.class);
        if (user != null) {
            return user;
        }
        user = userMapper.findByName(name);
        if (user != null) {
            cache.put(CACHE_USER_ + name, user);
        }
        return user;
    }

    public long create(User user) {
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            user.setCreator(user.getName());
            user.setModifier(user.getName());
            userMapper.insert(user);
            long id = user.getId();
            String content = String.format("创建%s用户,[邮箱:%s,是否上线:%s]",
                    user.getLoginName(), user.getEmail(), IS_USER_USED.test(user)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            cache.evict(CACHE_USER_ + "list");
        }
    }

    public void update(User user) {
        try {
            long id = user.getId();
            User existsUser = findById(id);
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            if (existsUser != null) {
                user.setModifier(u.getName());
                userMapper.update(user);
                String content = String.format("编辑%s用户,[邮箱:%s,是否上线:%s]->[邮箱:%s,是否上线:%s]",
                        existsUser.getLoginName(), existsUser.getEmail(), IS_USER_USED.test(existsUser), user.getLoginName(), IS_USER_USED.test(user)
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Edit.getValue(), u.getId(), content));
            }
        } finally {
            cache.evict(CACHE_USER_ + "list");
        }
    }

    public void delete(long id) {
        try {
            User user = findById(id);
            if (user != null) {
                cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
                userMapper.delete(id);
                String content = String.format("删除%s用户",
                        user.getLoginName()
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Delete.getValue(), u.getId(), content));
            }
        } finally {
            cache.evict(CACHE_USER_ + "list");
        }
    }

}
