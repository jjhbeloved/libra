package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.config.zk.ZKConfigLoader;
import cd.blog.humbird.libra.cli.model.ConfigValue;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by david on 2018/7/26.
 */
public class ConfigLoaderManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoaderManager.class);

    private List<ConfigLoader> configLoaders;
    private static volatile ConfigLoaderManager configLoaderManager;

    private ConfigLoaderManager() {
        init();
    }

    public static ConfigLoaderManager instance() {
        if (configLoaderManager == null) {
            synchronized (ConfigLoaderManager.class) {
                if (configLoaderManager == null) {
                    configLoaderManager = new ConfigLoaderManager();
                }
            }
        }
        return configLoaderManager;
    }

    /**
     * 获取指定的类对象
     *
     * @param clazz 类
     * @param <T>   具体类
     * @return 如果不存在返回null, 存在返回对应的对象
     */
    public <T> T getClassLoader(Class<T> clazz) {
        for (ConfigLoader configLoader : configLoaders) {
            if (clazz.isAssignableFrom(configLoader.getClass())) {
                return (T) configLoader;
            }
        }
        return null;
    }

    /**
     * 获取classloader下配置值(无缓存)
     *
     * @param key 值
     * @return val
     */
    public ConfigValue get(String key) {
        for (ConfigLoader configLoader : configLoaders) {
            ConfigValue configValue = configLoader.get(key);
            if (configValue != null) {
                return configValue;
            }
        }
        return null;
    }

    public void addConfigListener(ConfigListener configListener) {
        for (ConfigLoader configLoader : configLoaders) {
            configLoader.addConfigListener(configListener);
        }
    }

    public void removeConfigListener() {
        for (ConfigLoader configLoader : configLoaders) {
            configLoader.removeConfigListener();
        }
    }

    private void init() {
        configLoaders = Lists.newLinkedList();
        configLoaders.add(new ZKConfigLoader());
        for (ConfigLoader configLoader : configLoaders) {
            configLoader.init();
        }
    }
}
