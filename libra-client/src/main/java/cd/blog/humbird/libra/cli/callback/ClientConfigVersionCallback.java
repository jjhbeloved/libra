package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.model.ConfigEvent;
import cd.blog.humbird.libra.common.constant.LibraPath;
import cd.blog.humbird.libra.common.zk.ZKCli;

/**
 * Created by david on 2018/7/27.
 */
public class ClientConfigVersionCallback extends AbstractCallback {

    // /LIBRA/CALLBACK/config/${appName}/${key}
    private static final String CONFIG_KEYS_PATH = LibraPath.CALLBACK_CONFIG_KEYS_PATH_PREFIX + "/%s/%s";

    public ClientConfigVersionCallback(ZKCli zkCli) {
        super(zkCli);
    }

    public void call(ConfigEvent event) {
        String key = event.getKey();
        String version = event.getVersion();
        String value = event.getValue();
        String appPath = createAppPath(key);
        if (value == null) {

        }
    }

    /**
     * /LIBRA/CALLBACK/config/${appName}/${key}
     * 根据应用名查看到所有的keys
     *
     * @param key 值
     * @return path
     */
    private String createAppPath(String key) {
        return String.format(CONFIG_KEYS_PATH, ClientEnv.getAppName(), key);
    }
}
