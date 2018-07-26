package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.model.ConfigValue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by david on 2018/7/26.
 */
public class ConfigCache {

    private ConfigLoaderManager configLoaderManager = ConfigLoaderManager.instance();
    private static ConcurrentMap<String, ConfigValue> cachedConfig = new ConcurrentHashMap<>();

    /**
     * 从缓存获取值(如果不实用缓存则不读缓存)
     *
     * @param key 值
     * @return val
     */
    private ConfigValue getValueFromCache(String key) {
        if (ClientEnv.isReadCache()) {
            return cachedConfig.get(key);
        }
        return configLoaderManager.get(key);
    }
}
