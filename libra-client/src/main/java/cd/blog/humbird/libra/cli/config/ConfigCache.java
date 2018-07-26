package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.config.zk.ZKConfigLoader;
import cd.blog.humbird.libra.cli.model.ConfigValue;
import cd.blog.humbird.libra.common.Constants;
import cd.blog.humbird.libra.common.util.KeyUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by david on 2018/7/26.
 */
public class ConfigCache {

    private static volatile ConfigCache configCache;
    private String server;
    private ConfigLoaderManager configLoaderManager;
    private ZKConfigLoader zkConfigLoader;
    private static ConcurrentMap<String, ConfigValue> cachedConfig = new ConcurrentHashMap<>();
    // 本地的配置信息对应的value可以是${xxx}(有可能需要读取配置中心的值)
    private Properties localProps;

    private ConfigCache(String server) {
        this.server = server;
        if (StringUtils.isBlank(server)) {
            throw new NullPointerException("zk server is null.");
        }
        System.setProperty(ConfigLoader.KEY_ZOOKEEPER_ADDRESS, server);
        this.configLoaderManager = ConfigLoaderManager.instance();
        this.zkConfigLoader = this.configLoaderManager.getClassLoader(ZKConfigLoader.class);
    }

    public static ConfigCache instance() {
        return instance(ClientEnv.getZkserver());
    }

    public static ConfigCache instance(String server) {
        if (configCache == null) {
            synchronized (ConfigCache.class) {
                if (configCache == null) {
                    configCache = new ConfigCache(server);
                }
            }
        }
        return configCache;
    }

    public Properties getLocalProps() {
        return localProps;
    }

    public void setLocalProps(Properties localProps) {
        this.localProps = localProps;
    }

    public String get(String key) {
        ConfigValue configValue = getValue(key);
        if (configValue == null) {
            return null;
        }
        return configValue.getVal();
    }

    /**
     * 获取val, 如果存在本地配置, 优先读取本地配置
     * 如果本地配置存在${xxx}获取value的xxx去配置中心获取具体的值
     *
     * @param key 值
     * @return val
     */
    public ConfigValue getValue(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (localProps != null) {
            String val = localProps.getProperty(key);
            if (val != null) {
                if (KeyUtil.isReferenceValue(val)) {
                    key = KeyUtil.getReferencedKey(val);
                } else {
                    return new ConfigValue(val, Constants.LOCAL_VERSION);
                }
            }
        }
        return getValueFromCache(key);
    }


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
        ConfigValue configValue = configLoaderManager.get(key);
        if (configValue == null) {
            return null;
        }
        cachedConfig.put(key, configValue);
        return configValue;
    }
}
