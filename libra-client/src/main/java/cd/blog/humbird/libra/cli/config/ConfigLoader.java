package cd.blog.humbird.libra.cli.config;

/**
 * Created by david on 2018/7/10.
 */
public interface ConfigLoader {

    /**
     * Default placeholder prefix: "${"
     */
    String DEFAULT_PLACEHOLDER_PREFIX = "${";
    /**
     * Default placeholder suffix: "}"
     */
    String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    String KEY_PROPERTIES_FILE = "propertiesFile";
    String KEY_INCLUDE_LOCAL_PROPS = "includeLocalProps";

    /**
     * 初始化
     */
    void init();

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
