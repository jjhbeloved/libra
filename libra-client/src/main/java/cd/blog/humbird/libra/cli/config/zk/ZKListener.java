package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.common.util.ZKUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.zookeeper.Watcher.Event.EventType;

/**
 * 只监控有操作 path 路径的动作
 *
 * @author david
 * @since created by on 18/7/25 01:26
 */
public class ZKListener implements CuratorListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKListener.class);

    private ZKConfigLoader configLoader;

    public ZKListener(ZKConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        if (event.getType() == CuratorEventType.WATCHED) {
            WatchedEvent watchedEvent = event.getWatchedEvent();
            if (watchedEvent.getPath() != null) {
                try {
                    process(watchedEvent);
                } catch (Exception e) {
                    LOGGER.error("failed to process zk watchedEvent:{},exception:", watchedEvent, e);
                }
            }
        }
    }

    private void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        String key = ZKUtil.getConfigKey(path);
        if (key == null) {
            LOGGER.info("failed to get config key, path: {}", path);
            return;
        }
        EventType type = watchedEvent.getType();
        LOGGER.info("zk event received, path:{}, event:{}", path, type);
        if (type == EventType.NodeCreated || type == EventType.NodeDataChanged) {
            configChanged(key, type);
        } else if (type == EventType.NodeDeleted) {
            configDeleted(key);
        }
    }

    private void configChanged(String key, EventType eventType) {
        ZKValue curVal = configLoader.getZKValue(key);
        if (acceptChange(key, eventType, curVal)) {
            this.configLoader.changed(key, curVal);
        } else {
            LOGGER.info("ignored config event,key:{},value:{}", key, curVal);
        }
    }

    private void configDeleted(String key) {
        this.configLoader.deleted(key);
    }

    private boolean acceptChange(String key, EventType eventType, ZKValue curVal) {
        if (curVal == null || (eventType == EventType.NodeCreated && StringUtils.isBlank(curVal.getValue()))) {
            return false;
        }
        ZKValue exists = configLoader.getKeyValues().get(key);
        if (exists == null) {
            return false;
        }
        return curVal.getVersion().compareTo(exists.getVersion()) > 0;
    }
}
