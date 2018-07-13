package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.model.vo.User;
import cd.blog.humbird.libra.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

/**
 * 每个环境都对应一个注册管理器
 * <p>
 * Created by david on 2018/7/11.
 */
@Repository
public class EnvironmentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
    private static final String CACHE_ENV_LIST = "cache_env_list";
    private static final Predicate<Environment> IS_ONLINE = v -> v.getStatus() == 0;

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Resource(name = "caffeineClusterCache")
    private Cache cache;

    public List<Environment> findAll() {
        List<Environment> environments = cache.get(CACHE_ENV_LIST, List.class);
        if (CollectionUtils.isEmpty(environments)) {
            synchronized (this) {
                environments = cache.get(CACHE_ENV_LIST, List.class);
                if (CollectionUtils.isEmpty(environments)) {
                    environments = environmentMapper.findAll();
                    if (!CollectionUtils.isEmpty(environments)) {
                        cache.put(CACHE_ENV_LIST, environments);
                    }
                }
            }
        }
        return environments;
    }

    public Environment findById(long id) {
        List<Environment> environments = findAll();
        if (CollectionUtils.isEmpty(environments)) {
            return null;
        }
        for (Environment environment : environments) {
            if (id == environment.getId()) {
                return environment;
            }
        }
        return null;
    }

    public Environment findByName(String name) {
        List<Environment> environments = findAll();
        if (CollectionUtils.isEmpty(environments)) {
            return null;
        }
        for (Environment environment : environments) {
            if (StringUtils.equals(name, environment.getName())) {
                return environment;
            }
        }
        return null;
    }

    public long create(Environment environment) {
        try {
            User user = UserUtil.getUser();
            environment.setCreator(user.getName());
            environment.setModifier(user.getName());
            environmentMapper.create(environment);
            long id = environment.getId();
            String content = String.format("创建%s环境,IP:%s,是否上线:%s",
                    environment.getLabel(), environment.getIps(), IS_ONLINE.test(environment)
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Add.getValue(), user.getId(), user.getFrIp(), id, null, content));
            return id;
        } finally {
            cache.evict(CACHE_ENV_LIST);
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
            User user = UserUtil.getUser();
            environment.setModifier(user.getName());
            if (existsEnv != null) {
                environmentMapper.update(environment);
                String content = String.format("编辑%s环境,[IP:%s,是否上线:%s]->[IP:%s,是否上线:%s]",
                        existsEnv.getLabel(), existsEnv.getIps(), IS_ONLINE.test(existsEnv), environment.getIps(), IS_ONLINE.test(environment)
                );
                opLogRepository.insert(new OpLog(OpLogTypeEnum.Env_Edit.getValue(), user.getId(), user.getFrIp(), id, null, content));
            }
        } finally {
            cache.evict(CACHE_ENV_LIST);
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
            cache.evict(CACHE_ENV_LIST);
        }
    }

}
