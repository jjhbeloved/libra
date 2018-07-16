package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_USER_USED;

/**
 * Created by david on 2018/7/16.
 */
@Repository
public class UserRepository {

    private static final String CACHE_USER_LIST = "cache_user_list";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "caffeineClusterCache")
    private Cache cache;

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(long id) {
        List<User> users = findAll();
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        for (User user : users) {
            if (id == user.getId()) {
                return user;
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
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
            cache.evict(CACHE_USER_LIST);
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
            cache.evict(CACHE_USER_LIST);
        }
    }

}
