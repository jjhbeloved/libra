package cd.blog.humbird.libra.cli;

import cd.blog.humbird.libra.cli.util.PropertiesLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;

/**
 * @author david
 * @since created by on 18/7/5 01:17
 */
public class ClientEnvironment {

    private static final Logger logger = LoggerFactory.getLogger(ClientEnvironment.class);

    private static Properties props = null;
    private static String appName = null;
    private static String appVersion = null;
    private static String deployenv = null;
    private static String zkserver = null;
    private static boolean readCache = true;
    private static int syncInterval = 1800000;
    private static long startTime = System.currentTimeMillis();

    static {
        props = loadAppEnv();
        loadConfigFile();
    }

    /**
     * 加载 libra 内部缓存刷新变量
     */
    public static void loadConfigFile() {
        Properties props = PropertiesLoader.load("classpath:META-INF/libra.properties");
        if (props != null) {
            String cache = props.getProperty("readCache");
            String interval = props.getProperty("syncInterval");
            if (StringUtils.equalsIgnoreCase(cache, "false")) {
                readCache = false;
            }
            if (StringUtils.isNoneBlank(interval)) {
                syncInterval = Integer.parseInt(interval);
            }
        }
    }

    /**
     * 加载全局应用配置项
     * 环境/zk服务地址
     * </p>
     * 解析步骤
     * 1. web容器下 WEB-INF 目录下的 appenv 文件
     * 2. web容器下 webapps 目录下的 appenv 文件
     * 3. 获取 classpath 目录下的 appenv 文件
     * 4. {{预留扩展}}
     * 兜底. 校验若不存在取全局默认的兜底配置
     *
     * @return
     */
    public static Properties loadAppEnv() {
        Properties properties = null;
        URL url = ClientEnvironment.class.getProtectionDomain().getCodeSource().getLocation();
//        String path = url.getPath();
        String path = "/install_apps/intelliJ_bak/os/libra/libra-client/src/test/resources/WEB-INF/";
        properties = getProWithString(path, "WEB-INF");
        if (properties == null) {
            properties = getProWithString(path, "webapps");
        }
        // load from CLASSPATH
        if (properties == null) {
            properties = PropertiesLoader.load("classpath:appenv");
        }
        // {{预留扩展}}
        // check and using default values
        checkAppEnv(properties);
        logger.info("loaded appenv, env {} zkserver {}", deployenv, zkserver);
        return properties;
    }

    /**
     * 估计k获取v
     *
     * @param key k值
     * @return v值
     */
    public static String getVal(String key) {
        return props.getProperty(key);
    }

    private static void checkAppEnv(Properties properties) {
        zkserver = StringUtils.trim(properties.getProperty(Constants.KEY_ZKSERVER, Constants.DEFAULT_ZKSERVER));
        deployenv = StringUtils.trim(properties.getProperty(Constants.KEY_DEPLOYENV, Constants.DEFAULT_DEPLOYENV));
        if (appName == null) {
            properties.getProperty(Constants.APP_NAME);
        }
        if (appVersion == null) {
            properties.getProperty(Constants.APP_VERSION);
        }
    }

    private static Properties getProWithString(String path, String alias) {
        int idx = path.indexOf(alias);
        if (idx != -1) {
            String fs = path.substring(0, idx + (alias.length() + 1)).concat("appenv");
            return PropertiesLoader.load(fs);
        }
        return null;
    }

    public static String getAppName() {
        return Optional.ofNullable(appName).orElse("defaultAppName");
    }

    public static String getAppVersion() {
        return Optional.ofNullable(appVersion).orElse("defaultAppVersion");
    }

    public static String getZkserver() {
        return zkserver;
    }

    public static String getEnv() {
        return deployenv;
    }

    public static boolean isReadCache() {
        return readCache;
    }

    public static int getSyncInterval() {
        return syncInterval;
    }

    public static long getStartTime() {
        return startTime;
    }

}
