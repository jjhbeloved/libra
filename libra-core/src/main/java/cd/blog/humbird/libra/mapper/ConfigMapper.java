package cd.blog.humbird.libra.mapper;

import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.model.vo.ConfigCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by david on 2018/7/19.
 */
public interface ConfigMapper {

    Config findConfigById(long id);

    Config findByKeyAndProjectId(@Param("key") String key, @Param("projectId") long projectId);

    List<Config> findConfigByKey(String key);

    List<Config> findConfigByKeyParttern(String key);

    List<Config> findConfigByCreatorId(long creatorId);

    List<Config> findConfigByProjectId(long projectId);

    List<Config> findConfigs(@Param("criteria") ConfigCriteria criteria);

    void insert(Config config);

    void update(Config config);

    void delete(long id);

}
