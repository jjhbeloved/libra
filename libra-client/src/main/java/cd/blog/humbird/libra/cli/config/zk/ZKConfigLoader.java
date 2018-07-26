package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.config.AbstractConfigLoader;
import cd.blog.humbird.libra.cli.config.ConfigLoader;
import cd.blog.humbird.libra.cli.model.ConfigValue;
import cd.blog.humbird.libra.common.Constants;
import cd.blog.humbird.libra.common.util.ZKUtil;
import cd.blog.humbird.libra.common.zk.ZKCli;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author david
 * @since created by on 18/7/25 01:18
 */
public class ZKConfigLoader extends AbstractConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKConfigLoader.class);
    private AtomicBoolean closed = new AtomicBoolean(false);
    private String servers;
    private String namespace;
    private CuratorListener listener;
    private CuratorFramework cli;
    private ZKCli zkCli;

    public ZKConfigLoader() {
        this(null);
    }

    public ZKConfigLoader(String servers) {
        this(servers, null);
    }

    public ZKConfigLoader(String servers, String namespace) {
        this.servers = Optional.ofNullable(servers)
                .orElse(System.getProperty(ConfigLoader.KEY_ZOOKEEPER_ADDRESS, ClientEnv.getZkserver()));
        this.namespace = namespace;
    }

    @Override
    public void init() {
        this.listener = new ZKListener(this);
        this.cli = createCli();
        this.zkCli = new ZKCli(this.cli);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("zk class loader run shutdown hook...");
            destroy();
        }));
    }

    @Override
    public ConfigValue get(String key) {
        return getZKValue(key);
    }

    ZKValue getZKValue(String key) {
        return getValue(ZKUtil.getConfigPath(key));
    }

    private CuratorFramework createCli() {
        CuratorFramework cli = ZKUtil.createCuratorCli(servers);
        cli.getConnectionStateListenable().addListener((client, newState) -> {
            LOGGER.info("libra zookeeper state: " + newState);
            if (newState == ConnectionState.RECONNECTED) {
                try {
//                    trySyncConfig(true);
//                    feedbackManager.doReFeedback();
                } catch (Exception e) {
                    LOGGER.warn("failed to sync libra config", e);
                }
            }
//            recalculateMachineSerialNumber();
        });
        cli.getCuratorListenable().addListener(this.listener);
        cli.start();
        try {
            cli.getZookeeperClient().blockUntilConnectedOrTimedOut();
        } catch (Exception e) {
            LOGGER.error("failed to connect to zookeeper: " + servers, e);
        }
        return cli;
    }

    private void destroy() {
        if (this.cli == null) {
            return;
        }
        if (this.cli.getState() == CuratorFrameworkState.STARTED) {
            this.closed.set(true);
            this.cli.close();
        }
    }

    private ZKValue getValue(String path) {
        ZKValue value = null;
        Stat stat = new Stat();
        String val = this.zkCli.getWatched(path, stat);
        if (val != null) {
            value = new ZKValue();
            value.setVal(val);
            value.setVersion(String.format(Constants.VERSION_FORMAT, stat.getMtime(), stat.getVersion()));
        }
        return value;
    }
}
