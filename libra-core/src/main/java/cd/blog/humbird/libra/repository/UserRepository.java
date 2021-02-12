package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.model.domain.UserDO;
import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.model.po.UserPO;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.util.UserUtils;
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

    public PageInfo<UserPO> getUsers(UserCriteria user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserPO> userPOS = userMapper.findUsers(user);
        return new PageInfo<>(userPOS);
    }

    @Cacheable(value = "userLocalCache", key = LISTS, unless = " #result == null ")
    public List<UserPO> findAll() {
        return userMapper.findAll();
    }

    @Cacheable(value = "userLocalCache", key = "'id-' + #id", unless = "#result == null ")
    public UserPO findById(long id) {
        return userMapper.findById(id);
    }

    @Cacheable(value = "userLocalCache", key = "'name-' + #name", unless = "#result == null ")
    public UserPO findByName(String name) {
        return userMapper.findByName(name);
    }

    public long create(UserPO userPO) {
        try {
            UserDO u = UserUtils.getUser();
            userPO.setCreator(u.getName());
            userPO.setModifier(u.getName());
            userMapper.insert(userPO);
            long id = userPO.getId();
            String content = String.format("创建%s用户,[邮箱:%s,是否上线:%s]",
                    userPO.getLoginName(), userPO.getEmail(), IS_USER_USED.test(userPO)
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.User_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
        }
    }

    public void update(UserPO prvUserPO, UserPO userPO) {
        if (prvUserPO == null) {
            return;
        }
        long id = userPO.getId();
        try {
            UserDO u = UserUtils.getUser();
            userPO.setModifier(u.getName());
            userMapper.update(userPO);
            String content = String.format("编辑%s用户,[邮箱:%s,是否上线:%s]->[邮箱:%s,是否上线:%s]",
                    prvUserPO.getLoginName(), prvUserPO.getEmail(), IS_USER_USED.test(prvUserPO), userPO.getLoginName(), IS_USER_USED.test(userPO)
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.User_Edit.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + prvUserPO.getLoginName());
        }
    }

    public void delete(UserPO userPO) {
        if (userPO == null) {
            return;
        }
        long id = userPO.getId();
        try {
            UserDO u = UserUtils.getUser();
            userMapper.delete(id);
            String content = String.format("删除%s用户",
                    userPO.getLoginName()
            );
            opLogRepository.insert(new OpLogPO(OpLogTypeEnum.User_Delete.getValue(), u.getId(), content));
        } finally {
            Cache cache = cacheManager.getCache(UserLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + userPO.getLoginName());
        }
    }

}
