package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.util.UserUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_USER_USED;
import static cd.blog.humbird.libra.model.em.CacheEnum.UserLocalCache;

/**
 * Created by david on 2018/7/16.
 */
@Repository
public class UserRepository {

    private static final String LISTS = "lists";
    private static final String ID_ = "id-";
    private static final String NAME_ = "name-";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "localCacheManager")
    private CacheManager cacheManager;

    public PageInfo<User> getUsers(UserCriteria user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findUsers(user);
        return new PageInfo<>(users);
    }

    @Cacheable(value = "userLocalCache", key = LISTS, unless = " #result == null ")
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Cacheable(value = "userLocalCache", key = "'id-' + #id", unless = "#result == null ")
    public User findById(long id) {
        return userMapper.findById(id);
    }

    @Cacheable(value = "userLocalCache", key = "'name-' + #name", unless = "#result == null ")
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public long create(User user) {
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            user.setCreator(u.getName());
            user.setModifier(u.getName());
            userMapper.insert(user);
            long id = user.getId();
            String content = String.format("创建%s用户,[邮箱:%s,是否上线:%s]",
                    user.getLoginName(), user.getEmail(), IS_USER_USED.test(user)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
        }
    }

    public void update(User user) {
        long id = user.getId();
        User existsUser = findById(id);
        if (existsUser == null) {
            return;
        }
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            user.setModifier(u.getName());
            userMapper.update(user);
            String content = String.format("编辑%s用户,[邮箱:%s,是否上线:%s]->[邮箱:%s,是否上线:%s]",
                    existsUser.getLoginName(), existsUser.getEmail(), IS_USER_USED.test(existsUser), user.getLoginName(), IS_USER_USED.test(user)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Edit.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + existsUser.getLoginName());
        }
    }

    public void delete(long id) {
        User user = findById(id);
        if (user == null) {
            return;
        }
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            userMapper.delete(id);
            String content = String.format("删除%s用户",
                    user.getLoginName()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.User_Delete.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + user.getLoginName());
        }
    }

}
