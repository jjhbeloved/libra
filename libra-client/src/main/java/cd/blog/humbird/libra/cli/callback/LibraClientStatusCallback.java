package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.model.ClientStatus;
import cd.blog.humbird.libra.common.Constants;
import cd.blog.humbird.libra.common.util.JsonUtil;
import cd.blog.humbird.libra.common.util.SystemUtil;
import cd.blog.humbird.libra.common.zk.ZKCli;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author david
 * @since created by on 18/7/25 23:03
 */
public class LibraClientStatusCallback extends AbstractCallback {

    private static final Logger logger = LoggerFactory.getLogger(LibraClientStatusCallback.class);
    // /LIBRA/status/${appName}/${ip}-${pid}
    private static final String STATUS_PATH = Constants.STATUS_PATH_PREFIX + "/%s/%s-%s";

    @Override
    public void setZKCli(ZKCli zkCli) {
        super.zkCli = zkCli;
    }

    public void createStatus(String tag, String msg) {
        try {
            ClientStatus clientStatus = new ClientStatus();
            clientStatus.setTag(tag);
            clientStatus.setMsg(msg);
            String path = createPath();
            String content = JsonUtil.toJson(clientStatus);
            create(path, content);
            logger.info("libra client status callback success,path={},value={}", path, content);
        } catch (Exception e) {
            logger.error("libra client status callback failed,exception:{}", e.getMessage());
        }
    }

    private String createPath() {
        return String.format(STATUS_PATH, ClientEnv.getAppName(), SystemUtil.getIPv4Host(), SystemUtil.getPID());
    }
}
