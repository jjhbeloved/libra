package cd.blog.humbird.libra.common.zk;

import cd.blog.humbird.libra.common.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * @author david
 * @since created by on 18/7/25 00:46
 */
public class ZKCli {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZKCli.class);

    private CuratorFramework client;

    public ZKCli(CuratorFramework client) {
        this.client = client;
    }

    public boolean exists(String path) {
        try {
            return client.checkExists().forPath(path) != null;
        } catch (Exception e) {
            LOGGER.error("failed check exists:{},exception:{}", path, e.getMessage());
        }
        return false;
    }

    public boolean existsWatched(String path) throws Exception {
        try {
            return client.checkExists().watched().forPath(path) != null;
        } catch (Exception e) {
            LOGGER.error("failed check exists:{} watched,exception:{}", path, e.getMessage());
        }
        return false;
    }

    public String get(String path) {
        try {
            return new String(client.getData().forPath(path), Constants.CHARSET);
        } catch (Exception e) {
            LOGGER.error("failed get path:{},exception:{}", path, e.getMessage());
        }
        return null;
    }

    public String get(String path, Stat stat) {
        try {
            return new String(client.getData().storingStatIn(stat).forPath(path), Constants.CHARSET);
        } catch (Exception e) {
            LOGGER.error("failed get path:{} stat:{},exception:{}", path, stat, e.getMessage());
        }
        return null;
    }

    public String getWatched(String path) {
        try {
            return new String(client.getData().watched().forPath(path), Constants.CHARSET);
        } catch (Exception e) {
            LOGGER.error("failed to get path:{} watched,exception:{}", path, e.getMessage());
        }
        return null;
    }

    public String getWatched(String path, Stat stat) {
        try {
            return new String(client.getData().storingStatIn(stat).watched().forPath(path), Constants.CHARSET);
        } catch (Exception e) {
            LOGGER.error("failed to get path:{} stat:{},exception:{}", path, stat, e.getMessage());
        }
        return null;
    }

    public List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception e) {
            LOGGER.error("failed to get children path:{},exception:{}", path, e.getMessage());
        }
        return Collections.emptyList();
    }


    public boolean create(String path, String data) {
        return create(path, CreateMode.PERSISTENT, data);
    }

    public boolean create(String path, CreateMode mode, String data) {
        try {
            return create(path, mode, data.getBytes(Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("failed to create path:{},data:{},exception:{}", path, data, e.getMessage());
        }
        return false;
    }

    public boolean create(String path, byte[] data) {
        return create(path, CreateMode.PERSISTENT, data);
    }

    public boolean create(String path, CreateMode mode, byte[] data) {
        try {
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path, data);
            return true;
        } catch (Exception e) {
            LOGGER.error("failed to create path:{},data:{},exception:{}", path, data, e);
        }
        return false;
    }

    public boolean set(String path, String data) {
        try {
            return set(path, data.getBytes(Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("failed to set path:{},data:{},exception:{}", path, data, e.getMessage());
        }
        return false;
    }

    public boolean set(String path, byte[] data) {
        try {
            client.setData().forPath(path, data);
            return true;
        } catch (Exception e) {
            LOGGER.error("failed to set path:{},data{},exception:{}", path, data, e.getMessage());
        }
        return false;
    }

    public boolean createOrSet(String path, String data) {
        return createOrSet(path, CreateMode.PERSISTENT, data);
    }

    public boolean createOrSet(String path, CreateMode mode, String data) {
        try {
            return createOrSet(path, mode, data.getBytes(Constants.CHARSET));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("failed to createOrSet path:{},data:{},exception:{}", path, data, e.getMessage());
        }
        return false;
    }

    public boolean createOrSet(String path, byte[] data) {
        return createOrSet(path, CreateMode.PERSISTENT, data);
    }

    public boolean createOrSet(String path, CreateMode mode, byte[] data) {
        if (exists(path)) {
            return set(path, data);
        } else {
            return create(path, mode, data);
        }
    }

    public void deleteAndChildren(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            LOGGER.error("failed to delete path:{},exception:{}", path, e.getMessage());
        }
    }
}
