package cd.blog.humbird.libra.register;

import cd.blog.humbird.libra.exception.ZookeeperRegisterException;
import cd.blog.humbird.libra.mapper.ZKCli;
import cd.blog.humbird.libra.util.EncodeUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by david on 2018/7/11.
 */
public class ZookeeperRegister implements Register {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRegister.class);

    private String servers;
    private String namespace;
    private String parentPath = "/HUMBIRD/LIBRA";
    private String contextNode = "CONTEXTVALUE";
    private String timestampNode = "TIMESTAMP";

    private ZKCli zkCli;

    public ZookeeperRegister(String servers) {
        this(servers, null);
    }

    public ZookeeperRegister(String servers, String namespace) {
        this.servers = servers;
        this.namespace = namespace;
        zkCli = new ZKCli(servers, namespace);
    }

    @Override
    public void init() throws Exception {
        zkCli.start();
    }

    @Override
    public void registerContextValue(String key, String value) {
        try {
            set(this.parentPath + "/" + key + "/" + this.contextNode, value);
        } catch (Exception e) {
            throw new ZookeeperRegisterException("Register config[" + key + "]'s context value[" + value + "] to zookeeper failed.", e);
        }
    }

    @Override
    public void registerAndPushContextValue(String key, String value) {
        try {
            set(this.parentPath + "/" + key + "/" + this.contextNode, value);
            set(this.parentPath + "/" + key + "/" + this.timestampNode, EncodeUtil.long2byte(System.currentTimeMillis()));
        } catch (Exception e) {
            throw new ZookeeperRegisterException("Push config[" + key + "]'s context and timestamp value[" + value + "] to zookeeper failed.", e);
        }
    }

    @Override
    public void registerDefaultValue(String key, String value) {
        registerGroupValue(key, null, value);
    }

    @Override
    public void registerAndPushDefaultValue(String key, String value) {
        registerAndPushGroupValue(key, null, value);
    }

    @Override
    public void registerGroupValue(String key, String group, String value) {
        try {
            String path = getPath(key, group);
            set(path, value);
        } catch (Exception e) {
            throw new ZookeeperRegisterException("Push config[" + key + "/" + group + "]'s group value[" + value + "] to zookeeper failed.", e);
        }
    }

    @Override
    public void registerAndPushGroupValue(String key, String group, String value) {
        try {
            String path = getPath(key, group);
            String tPath = path + "/" + timestampNode;
            set(tPath, System.currentTimeMillis());
            set(path, value);
        } catch (Exception e) {
            throw new ZookeeperRegisterException("Push config[" + key + "/" + group + "]'s group and timestamp value[" + value + "] to zookeeper failed.", e);
        }
    }

    @Override
    public void unregister(String key) {
        unregister(key, null);
    }

    @Override
    public void unregister(String key, String group) {
        try {
            String path = getPath(key, group);
            if (zkCli.exists(path)) {
                zkCli.deleteAndChildren(path);
            }
        } catch (Exception e) {
            throw new ZookeeperRegisterException("Unregister config[" + key + "/" + group + "] from zookeeper failed.", e);
        }
    }

    @Override
    public String get(String key) {
        return get(key, null);
    }

    @Override
    public String get(String key, String group) {
        try {
            String path = getPath(key, group);
            return zkCli.get(path);
        } catch (Exception e) {
            LOGGER.warn("Read config[{}/{}] from zookeeper failed.", key, group);
        }
        return null;
    }

    @Override
    public String getAddresses() {
        return servers;
    }

    @Override
    public String getRemoteDataVersion(String key) {
        String path = this.parentPath + "/" + key;
        try {
            Stat stat = zkCli.getStat(path);
            return String.format("%s-%s", stat.getMtime(), stat.getVersion());
        } catch (Exception e) {
            LOGGER.warn("get data version from zookeeper fail,error:{}", e);
        }
        return null;
    }

    @Override
    public String getValueWithNoBasePath(String var1) throws Exception {
        return null;
    }

    @Override
    public List<String> getChildrenValueWithNoBasePath(String var1) throws Exception {
        return null;
    }

    @Override
    public List<String> getChildrenWithNoBasePath(String var1) throws Exception {
        return null;
    }

    @Override
    public void destroy() {
        zkCli.close();
    }

    private String getPath(String key, String group) {
        if (StringUtils.hasText(group)) {
            return parentPath + "/" + key + "/" + group;
        } else {
            return parentPath + "/" + key;
        }
    }

    private void set(String path, long value) throws Exception {
        this.set(path, EncodeUtil.long2byte(value));
    }

    private void set(String path, String value) throws Exception {
        if (value == null) {
            LOGGER.warn("Set null config value to zk[{}] with path[{}].", servers, path);
        } else {
            this.set(path, value.getBytes(EncodeUtil.CHARSET));
        }
    }

    private void set(String path, byte[] value) throws Exception {
        if (zkCli.exists(path)) {
            zkCli.set(path, value);
        } else {
            zkCli.create(path, value);
        }
    }

}
