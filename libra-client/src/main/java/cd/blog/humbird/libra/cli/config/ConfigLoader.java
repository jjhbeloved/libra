package cd.blog.humbird.libra.cli.config;

import cd.blog.humbird.libra.cli.model.ConfigValue;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public interface ConfigLoader {

    String KEY_PROPERTIES_FILE = "propertiesFile";
    String KEY_INCLUDE_LOCAL_PROPS = "includeLocalProps";
    String KEY_ZOOKEEPER_ADDRESS = "zookeeperAddress";

    /**
     * 初始化
     */
    void init();

    /**
     * 根据 key 获取配置环境下对应的信息
     *
     * @param key 键
     * @return 值信息
     */
    ConfigValue get(String key);

    /**
     * 添加配置监听器
     *
     * @param configListener 置监听器
     */
    void addConfigListener(ConfigListener configListener);

    /**
     * 情况配置监听器
     */
    void removeConfigListener();

}
