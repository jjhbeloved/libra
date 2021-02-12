package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.model.po.ConfigPO;
import cd.blog.humbird.libra.model.po.ConfigInstancePO;
import cd.blog.humbird.libra.model.vo.ConfigCriteria;
import cd.blog.humbird.libra.model.vo.ConfigInstanceCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author david
 * @since created by on 2018/7/19 23:29
 */
public interface ConfigMapper {

	ConfigPO getConfigById(long id);

	ConfigPO getConfigByKeyAndProjectId(@Param("key") String key, @Param("projectId") long projectId);

	List<ConfigPO> listConfigsByKey(String key);

	List<ConfigPO> listConfigsByKeyParttern(String key);

	List<ConfigPO> listConfigsByCreatorId(long creatorId);

	List<ConfigPO> listConfigsByProjectId(long projectId);

	List<ConfigPO> listConfigs(@Param("criteria") ConfigCriteria criteria);

	void insertConfig(ConfigPO configPO);

	void updateConfig(ConfigPO configPO);

	void deleteConfig(long id);

	// ################ config instance #################

	ConfigInstancePO getConfigInstanceById(long id);

	ConfigInstancePO getConfigInstanceByConfigIdAndEnvId(@Param("configId") long configId, @Param("envId") long envId);

	List<ConfigInstancePO> listConfigInstancesByCreatorId(long creatorId);

	List<ConfigInstancePO> listConfigInstances(@Param("criteria") ConfigInstanceCriteria criteria);

	void insertConfigInstance(ConfigInstancePO configInstancePO);

	void updateConfigInstance(ConfigInstancePO configInstancePO);

	void deleteConfigInstance(long id);
}
