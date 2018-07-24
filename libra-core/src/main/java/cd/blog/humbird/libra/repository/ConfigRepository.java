package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.exception.BusinessException;
import cd.blog.humbird.libra.mapper.ConfigMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.model.em.CacheEnum.ConfigLocalCache;

/**
 * Created by david on 2018/7/20.
 */
@Repository
public class ConfigRepository {

    private static final String ID_ = "id-";
    private static final String PRO_ID_ = "proId-";

    @Resource(name = "localCacheManager")
    private CacheManager cacheManager;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private OpLogRepository opLogRepository;

    @Cacheable(value = "configLocalCache", key = "'id-' + #id", unless = "#result == null")
    public Config findConfigById(long id) {
        return configMapper.findConfigById(id);
    }

    @Cacheable(value = "configLocalCache", key = "'proId-' + #projectId", unless = "#result == null || #result.size() == 0")
    public List<Config> findConfigByProjectId(long projectId) {
        return configMapper.findConfigByProjectId(projectId);
    }

//    @Cacheable(value = "configLocalCache", key = "'key-' + #key + '-proId-' + #projectId", unless = "#result == null")
//    public Config findConfigByKeyAndProjectId(String key, long projectId) {
//        return configMapper.findConfigByKeyAndProjectId(key, projectId);
//    }

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
            Cache cache = cacheManager.getCache(ConfigLocalCache.getCode());
//            cache.evict("proId-" + projectId);
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
            Cache cache = cacheManager.getCache(ConfigLocalCache.getCode());
            cache.evict(PRO_ID_ + projectId);
            cache.evict(ID_ + id);
        }
    }
}
