package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.mapper.SystemSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by david on 2018/7/12.
 */
@Repository
public class SystemSettingRepository {

    @Autowired
    private SystemSettingMapper systemSettingMapper;

    @Resource(name = "localCacheManager")
    private CacheManager cacheManager;

    public String getVal(String key) {
        return systemSettingMapper.findByKey(key);
    }

    public void update(String key, String value) {
        systemSettingMapper.update(key, value);
    }

    public boolean getBool(String key) {
        String value = getVal(key);
        return Boolean.parseBoolean(value);
    }

}
