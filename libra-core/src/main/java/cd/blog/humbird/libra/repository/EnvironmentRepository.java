package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.vo.User;
import cd.blog.humbird.libra.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_ENV_USED;
import static cd.blog.humbird.libra.model.em.CacheEnum.EnvLocalCache;

/**
 * 每个环境都对应一个注册管理器
 * <p>
 * Created by david on 2018/7/11.
 */
@Repository
public class EnvironmentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
    private static final String LISTS = "lists";
    private static final String ID_ = "id-";
    private static final String NAME_ = "name-";

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "localCacheManager")
    private CacheManager cacheManager;

    @Cacheable(value = "envLocalCache", key = LISTS, unless = "#result == null || #result.size() == 0")
    public List<Environment> findAll() {
        return environmentMapper.findAll();
    }

    @Cacheable(value = "envLocalCache", key = "'id-' + #id", unless = "#result == null")
    public Environment findById(long id) {
        return environmentMapper.findById(id);
    }

    @Cacheable(value = "envLocalCache", key = "'name-' + #name", unless = "#result == null")
    public Environment findByName(String name) {
        return environmentMapper.findByName(name);
    }

    public long create(Environment environment) {
        try {
            User user = UserUtil.getUser();
            environment.setCreator(user.getName());
            environment.setModifier(user.getName());
            environmentMapper.insert(environment);
            long id = environment.getId();
            String content = String.format("创建%s环境,[IP:%s,是否上线:%s]",
                    environment.getLabel(), environment.getIps(), IS_ENV_USED.test(environment)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Add.getValue(), user.getId(), user.getFrIp(), id, null, content));
            return id;
        } finally {
            Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
            cache.evict(LISTS);
        }
    }

    /**
     * 只允许修改 IPS 和 状态
     *
     * @param environment
     */
    public void update(Environment environment) {
        long id = environment.getId();
        Environment existsEnv = findById(id);
        if (existsEnv == null) {
            return;
        }
        try {
            User u = UserUtil.getUser();
            environment.setModifier(u.getName());
            environmentMapper.update(environment);
            String content = String.format("编辑%s环境,[IP:%s,是否上线:%s]->[IP:%s,是否上线:%s]",
                    existsEnv.getLabel(), existsEnv.getIps(), IS_ENV_USED.test(existsEnv), environment.getIps(), IS_ENV_USED.test(environment)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Edit.getValue(), u.getId(), u.getFrIp(), id, null, content));
        } finally {
            Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + environment.getName());
        }
    }

    public void delete(long id) {
        Environment environment = findById(id);
        if (environment == null) {
            return;
        }
        try {
            environmentMapper.delete(id);
            User user = UserUtil.getUser();
            String content = "删除" + environment.getLabel() + "环境";
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Delete.getValue(), user.getId(), user.getFrIp(), id, null, content));

        } finally {
            Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
            cache.evict(LISTS);
            cache.evict(ID_ + id);
            cache.evict(NAME_ + environment.getName());
        }
    }

}
