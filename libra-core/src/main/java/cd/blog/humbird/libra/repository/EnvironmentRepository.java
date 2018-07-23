package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.model.vo.User;
import cd.blog.humbird.libra.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_ENV_USED;

/**
 * 每个环境都对应一个注册管理器
 * <p>
 * Created by david on 2018/7/11.
 */
@Repository
public class EnvironmentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
    private static final String CACHE_ENV_ = "cache_env_";

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "localClusterCache")
    private Cache cache;

    public List<Environment> findAll() {
        List<Environment> environments = cache.get(CACHE_ENV_ + "list", List.class);
        if (!CollectionUtils.isEmpty(environments)) {
            return environments;
        }
        synchronized (this) {
            environments = cache.get(CACHE_ENV_ + "list", List.class);
            if (CollectionUtils.isEmpty(environments)) {
                environments = environmentMapper.findAll();
                if (!CollectionUtils.isEmpty(environments)) {
                    cache.put(CACHE_ENV_ + "list", environments);
                }
            }
        }
        return environments;
    }

    public Environment findById(long id) {
        Environment environment = cache.get(CACHE_ENV_ + id, Environment.class);
        if (environment != null) {
            return environment;
        }
        environment = environmentMapper.findById(id);
        if (environment != null) {
            cache.put(CACHE_ENV_ + id, environment);
        }
        return environment;
    }

    public Environment findByName(String name) {
        Environment environment = cache.get(CACHE_ENV_ + name, Environment.class);
        if (environment != null) {
            return environment;
        }
        environment = environmentMapper.findByName(name);
        if (environment != null) {
            cache.put(CACHE_ENV_ + name, environment);
        }
        return environment;
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
            cache.evict(CACHE_ENV_ + "list");
        }
    }

    /**
     * 只允许修改 IPS 和 状态
     *
     * @param environment
     */
    public void update(Environment environment) {
        try {
            long id = environment.getId();
            Environment existsEnv = findById(id);
            User u = UserUtil.getUser();
            if (existsEnv != null) {
                environment.setModifier(u.getName());
                environmentMapper.update(environment);
                String content = String.format("编辑%s环境,[IP:%s,是否上线:%s]->[IP:%s,是否上线:%s]",
                        existsEnv.getLabel(), existsEnv.getIps(), IS_ENV_USED.test(existsEnv), environment.getIps(), IS_ENV_USED.test(environment)
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Edit.getValue(), u.getId(), u.getFrIp(), id, null, content));
            }
        } finally {
            cache.evict(CACHE_ENV_ + "list");
        }
    }

    public void delete(long id) {
        try {
            Environment environment = findById(id);
            if (environment != null) {
                environmentMapper.delete(id);
                User user = UserUtil.getUser();
                String content = "删除" + environment.getLabel() + "环境";
                opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Delete.getValue(), user.getId(), user.getFrIp(), id, null, content));
            }

        } finally {
            cache.evict(CACHE_ENV_ + "list");
        }
    }

}
