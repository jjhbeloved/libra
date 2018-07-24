package cd.blog.humbird.libra.common.util;

import cd.blog.humbird.libra.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author david
 * @since created by on 18/7/25 01:12
 */
public class ZKUtil {
    public static CuratorFramework createCuratorCli(String servers) {
        return createCuratorCli(servers, null);
    }

    public static CuratorFramework createCuratorCli(String servers, String namespace) {
        return CuratorFrameworkFactory.builder()
                .connectString(servers)
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(30 * 1000)
                .retryPolicy(new RetryNTimes(3, 1000))
                .namespace(namespace)
                .build();
    }

    public static String getConfigKey(String path) {
        if (!StringUtils.startsWith(path, Constants.CONFIG_PATH)) {
            return null;
        }
        return path.substring(Constants.CONFIG_PATH.length() + 1);
    }

    public static String getConfigPath(String key) {
        return Constants.CONFIG_PATH + Constants.PATH_SEPARATOR + key;
    }
}
