package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.model.ClientStatus;
import cd.blog.humbird.libra.cli.model.ConfigEvent;
import cd.blog.humbird.libra.common.constant.LibraPath;
import cd.blog.humbird.libra.common.util.JsonUtils;
import cd.blog.humbird.libra.common.util.SystemUtils;
import cd.blog.humbird.libra.common.zk.ZkCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author david
 * @since created by on 18/7/25 23:03
 */
public class LibraClientStatusCallback extends AbstractCallback {

    private static final Logger logger = LoggerFactory.getLogger(LibraClientStatusCallback.class);
    /**
     * 应用状态地址: /LIBRA/CALLBACK/status/${appName}/${ip}-${pid}
     */
    private static final String STATUS_PATH = LibraPath.CALLBACK_STATUS_PATH_PREFIX + "/%s/%s-%s";

    public LibraClientStatusCallback(ZkCli zkCli) {
        super(zkCli);
    }

    @Override
    public void call(ConfigEvent event) {
        try {
            ClientStatus clientStatus = new ClientStatus();
            clientStatus.setTag(event.getKey());
            clientStatus.setMsg(event.getValue());
            String path = createPath();
            String content = JsonUtils.toJson(clientStatus);
            create(path, content);
            logger.info("libra client status callback success,path={},value={}", path, content);
        } catch (Exception e) {
            logger.error("libra client status callback failed,exception:{}", e.getMessage());
        }
    }

    private String createPath() {
        return String.format(STATUS_PATH, ClientEnv.getAppName(), SystemUtils.getIPv4Host(), SystemUtils.getPID());
    }
}
