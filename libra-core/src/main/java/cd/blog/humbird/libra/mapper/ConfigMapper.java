package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.entity.ConfigInstance;
import cd.blog.humbird.libra.model.vo.ConfigCriteria;
import cd.blog.humbird.libra.model.vo.ConfigInstanceCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by david on 2018/7/19.
 */
public interface ConfigMapper {

    Config findConfigById(long id);

    Config findConfigByKeyAndProjectId(@Param("key") String key, @Param("projectId") long projectId);

    List<Config> findConfigByKey(String key);

    List<Config> findConfigByKeyParttern(String key);

    List<Config> findConfigByCreatorId(long creatorId);

    List<Config> findConfigByProjectId(long projectId);

    List<Config> findConfigs(@Param("criteria") ConfigCriteria criteria);

    void insertConfig(Config config);

    void updateConfig(Config config);

    void deleteConfig(long id);

    // ################ config instance #################

    ConfigInstance findConfigInstanceById(long id);

    List<ConfigInstance> findConfigInstanceByCreatorId(long creatorId);

    ConfigInstance findConfigInstanceByConfigIdAndEnvId(@Param("configId") long configId, @Param("envId") long envId);

    List<ConfigInstance> findConfigInstances(@Param("criteria") ConfigInstanceCriteria criteria);

    void insertConfigInstance(ConfigInstance configInstance);

    void updateConfigInstance(ConfigInstance configInstance);

    void deleteConfigInstance(long id);
}
