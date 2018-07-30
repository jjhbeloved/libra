package cd.blog.humbird.libra.common.constant;

/**
 * Created by david on 2018/7/30.
 */
public class Parameter {

    /**
     * 系统环境变量: APP NAME KEY
     */
    public static final String APP_NAME = "app_name";
    /**
     * 系统环境变量: APP VERSION KEY
     */
    public static final String APP_VERSION = "app_version";
    /**
     * VERSION FORMAT
     */
    public static final String VERSION_FORMAT = "%s-%s";
    /**
     * LOCAL APP VERSION
     */
    public static final String LOCAL_VERSION = "9.9.9-RELEASE"; // FIXME 读取本地应用版本
    /**
     * 部署环境 dev/test/perf/pre/prod
     */
    public static final String KEY_DEPLOYENV = "deploy_env";
    /**
     * 部署环依赖的zk server
     */
    public static final String KEY_ZKSERVER = "zk_server";
    /**
     * 默认的部署环境
     */
    public static final String DEFAULT_DEPLOYENV = "dev";
    /**
     * 默认的zk server
     */
    public static final String DEFAULT_ZKSERVER = "localhost:2181";
    /**
     * 数据编码
     */
    public static final String CHARSET = "UTF-8";
    /**
     * Default placeholder prefix: "${"
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    /**
     * Default placeholder suffix: "}"
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
}
