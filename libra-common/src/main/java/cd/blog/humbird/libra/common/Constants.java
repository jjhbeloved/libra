package cd.blog.humbird.libra.common;

/**
 * @author david
 * @since created by on 18/7/5 01:15
 */
public class Constants {

    public static final String CHARSET = "UTF-8";
    public static final String VERSION_FORMAT = "%s-%s";

    public static final String APP_NAME = "app_name";
    public static final String APP_VERSION = "app_version";
    public static final String LOCAL_VERSION = "9.9.9-RELEASE";

    public static final String KEY_DEPLOYENV = "deploy_env";
    public static final String KEY_ZKSERVER = "zk_server";
    public static final String DEFAULT_DEPLOYENV = "dev";
    public static final String DEFAULT_ZKSERVER = "localhost:2181";


    public static final String CONFIG_PATH = "/HUMBIRD/LIBRA";
    public static final String CALLBACK_STATUS_PATH_PREFIX = "/LIBRA/CALLBACK/status";
    public static final String CALLBACK_CONFIG_KEYS_PATH_PREFIX = "/LIBRA/CALLBACK/config/";
    public static final String PATH_SEPARATOR = "/";

    /**
     * Default placeholder prefix: "${"
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    /**
     * Default placeholder suffix: "}"
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
}
