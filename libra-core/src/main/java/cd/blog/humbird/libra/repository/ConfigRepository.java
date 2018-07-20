package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.OpLogTypeEnum;
import cd.blog.humbird.libra.exception.BusinessException;
import cd.blog.humbird.libra.mapper.ConfigMapper;
import cd.blog.humbird.libra.util.UserUtil;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by david on 2018/7/20.
 */
@Repository
public class ConfigRepository {

    private static final String CACHE_CONFIG_ID_ = "cache_config_id_";
    private static final String CACHE_CONFIG_PROJECT_ = "cache_config_p_";
    private ConcurrentMap<String, ReentrantLock> configLocks = Maps.newConcurrentMap();

    @Resource(name = "localClusterShortCache")
    private Cache cache;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    public Config findConfigById(long id) {
        String key = CACHE_CONFIG_ID_ + id;
        Config config = cache.get(key, Config.class);
        if (config != null) {
            return config;
        }
        config = configMapper.findConfigById(id);
        cache.put(key, config);
        return config;
    }

    public List<Config> findConfigByProjectId(long projectId) {
        String key = CACHE_CONFIG_PROJECT_ + projectId;
        List<Config> configs = cache.get(key, List.class);
        if (!CollectionUtils.isEmpty(configs)) {
            return configs;
        }
        ReentrantLock reentrantLock = configLocks.get(key);
        if (reentrantLock == null) {
            reentrantLock = configLocks.putIfAbsent(key, new ReentrantLock());
        }
        try {
            reentrantLock.lock();
            configs = cache.get(key, List.class);
            if (!CollectionUtils.isEmpty(configs)) {
                return configs;
            }
            configs = configMapper.findConfigByProjectId(projectId);
            cache.put(key, configs);
        } finally {
            reentrantLock.unlock();
        }
        return configs;
    }

    public Config findConfigByKeyAndProjectId(String key, long projectId) {
        List<Config> configs = findConfigByProjectId(projectId);
        return configs.stream()
                .filter(v -> StringUtils.equals(key, v.getKey()))
                .findFirst()
                .orElse(null);
    }

    public long createConfig(Config config) {
        Config existsConfig = configMapper.findConfigByKeyAndProjectId(config.getKey(), config.getProjectId());
        long projectId = config.getProjectId();
//        Project project = this.projectDao.getProject(configFound.getProjectId());
        if (existsConfig != null) {
//            throw BusinessException("该配置项已存在(project: " + (project != null ? project.getName() : "***") + ", desc: " + configFound.getDesc() + ")!");
            throw new BusinessException("该配置项已存在desc: " + existsConfig.getDesc() + ")!");
        }
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            config.setCreator(u.getName());
            config.setModifier(u.getName());
            config.setCreatorId(u.getId());
            configMapper.insertConfig(config);
            long id = config.getId();
            String content = String.format("创建%s项目,配置%s",
                    "xx", config.getKey()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Config_Add.getValue(), u.getId(), content));
            return id;
        } finally {
            cache.evict(CACHE_CONFIG_PROJECT_ + projectId);
        }
    }

    public void deleteConfig(long id) {
        Config config = findConfigById(id);
        if (config == null) {
            return;
        }
        long projectId = config.getProjectId();
        try {
            cd.blog.humbird.libra.model.vo.User u = UserUtil.getUser();
            configMapper.deleteConfig(id);
            String content = String.format("删除%s项目,配置%s",
                    "xx", config.getKey()
            );
            opLogRepository.insert(new OpLog(OpLogTypeEnum.Config_Delete.getValue(), u.getId(), content));
        } finally {
            cache.evict(CACHE_CONFIG_ID_ + id);
            cache.evict(CACHE_CONFIG_PROJECT_ + projectId);
        }
    }
}
