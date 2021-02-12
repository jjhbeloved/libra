package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.cli.callback.Callback;
import cd.blog.humbird.libra.cli.callback.CallbackManager;
import cd.blog.humbird.libra.cli.callback.ClientConfigVersionCallback;
import cd.blog.humbird.libra.cli.callback.LibraClientStatusCallback;
import cd.blog.humbird.libra.cli.config.AbstractConfigLoader;
import cd.blog.humbird.libra.cli.config.ConfigLoader;
import cd.blog.humbird.libra.cli.model.ConfigEvent;
import cd.blog.humbird.libra.cli.model.ConfigValue;
import cd.blog.humbird.libra.common.constant.Parameter;
import cd.blog.humbird.libra.common.util.ZkUtils;
import cd.blog.humbird.libra.common.zk.ZkCli;
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
public class ZkConfigLoader extends AbstractConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkConfigLoader.class);
    private AtomicBoolean closed = new AtomicBoolean(false);
    private String servers;
    private String namespace;
    private CuratorListener listener;
    private CuratorFramework cli;
    private ZkCli zkCli;
    private CallbackManager callbackManager;
    private Callback clientConfigCallback;
    private Callback clientStatusCallback;

    public ZkConfigLoader() {
        this(null);
    }

    public ZkConfigLoader(String servers) {
        this(servers, null);
    }

    public ZkConfigLoader(String servers, String namespace) {
        this.servers = Optional.ofNullable(servers)
                .orElse(System.getProperty(ConfigLoader.KEY_ZOOKEEPER_ADDRESS, ClientEnv.getZkserver()));
        this.namespace = namespace;
    }

    @Override
    public void init() {
        this.listener = new ZkListener(this);
        this.cli = createCli();
        this.zkCli = new ZkCli(cli);
        this.callbackManager = CallbackManager.instance(zkCli);
        clientConfigCallback = callbackManager.getClassLoader(ClientConfigVersionCallback.class);
        clientStatusCallback = callbackManager.getClassLoader(LibraClientStatusCallback.class);
        clientStatusCallback.call(new ConfigEvent(null, null));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("zk class loader run shutdown hook...");
            destroy();
        }));
    }

    public void callClientStatus(ConfigEvent event) {
        clientStatusCallback.call(event);
    }

    public void callClientConfigVersion(ConfigEvent event) {
        clientConfigCallback.call(event);
    }

    public void changed(String key, ZkValue zkValue) {
        refresh(new ConfigEvent(key, zkValue.getVal(), zkValue.getVersion()));
    }

    public void deleted(String key) {
        refresh(new ConfigEvent(key));
    }

    @Override
    public ConfigValue get(String key) {
        return getZKValue(key);
    }

    ZkValue getZKValue(String key) {
        return getValue(ZkUtils.getConfigPath(key));
    }

    private CuratorFramework createCli() {
        CuratorFramework cli = ZkUtils.createCuratorCli(servers);
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

    private ZkValue getValue(String path) {
        ZkValue value = null;
        Stat stat = new Stat();
        String val = this.zkCli.getWatched(path, stat);
        if (val != null) {
            value = new ZkValue();
            value.setVal(val);
            value.setVersion(String.format(Parameter.VERSION_FORMAT, stat.getMtime(), stat.getVersion()));
        } else {
            LOGGER.info("not found {} val.", path);
        }
        return value;
    }

    /**
     * 请求变更事件给配置监听器
     *
     * @param event 时间
     */
    private void refresh(ConfigEvent event) {
        if (getConfigListener() != null) {
            getConfigListener().refresh(event);
        }
    }
}
