package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.common.zk.ZKCli;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author david
 * @since created by on 18/7/25 22:19
 */
public abstract class AbstractCallback implements Callback {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCallback.class);
    protected ConcurrentHashMap<String, String> ephemeralNodes = new ConcurrentHashMap<>();
    protected ZKCli zkCli;

    @Override
    public void create(String path, String val) {
        zkCli.createOrSet(path, CreateMode.EPHEMERAL, val);
        ephemeralNodes.put(path, val);
    }

    @Override
    public void reCreate() {
        for (Map.Entry<String, String> entry : ephemeralNodes.entrySet()) {
            String path = entry.getKey();
            String val = entry.getValue();
            if (!zkCli.exists(path)) {
                zkCli.create(path, CreateMode.EPHEMERAL, val);
            }
            LOGGER.info("re create ephemeral node success ,path:{},value:{}", path, val);
        }
    }

    @Override
    public void delete(String path) {
        zkCli.deleteAndChildren(path);
        ephemeralNodes.remove(path);
    }
}
