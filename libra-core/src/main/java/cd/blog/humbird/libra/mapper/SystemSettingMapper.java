package cd.blog.humbird.libra.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by david on 2018/7/12.
 */
public interface SystemSettingMapper {

    String findByKey(String key);

    void update(@Param("key") String key, @Param("value") String value);

}
