package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.model.ConfigValue;

/**
 * Created by david on 2018/7/10.
 */
public interface ConfigLoader {

    String KEY_PROPERTIES_FILE = "propertiesFile";
    String KEY_INCLUDE_LOCAL_PROPS = "includeLocalProps";
    String KEY_ZOOKEEPER_ADDRESS = "zookeeperAddress";

    /**
     * 初始化
     */
    void init();

    ConfigValue get(String key);

    /**
     * 添加配置监听器
     *
     * @param configListener 置监听器
     */
    void addConfigListener(ConfigListener configListener);

    /**
     * 情况配置监听器
     *
     * @param configListener 置监听器
     */
    void removeConfigListener(ConfigListener configListener);

}
