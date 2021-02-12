package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.model.ConfigValue;
import org.apache.commons.lang3.StringUtils;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public class Config {

    private static volatile Config config;
    private static ConfigCache configCache = ConfigCache.instance();

    private Config() {

    }

    public static Config instance() {
        if (config == null) {
            synchronized (Config.class) {
                if (config == null) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    public String get(String key) {
        ConfigValue configValue = getValue(key);
        if (configValue == null) {
            return null;
        }
        return configValue.getVal();
    }

    public ConfigValue getValue(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        ConfigValue configValue = configCache.getValue(key);
        configCache.callClientConfigVersion(key, configValue);
        return configValue;
    }
}
