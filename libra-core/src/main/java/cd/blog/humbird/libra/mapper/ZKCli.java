//package cd.blog.humbird.libra.mapper;
//
//import cd.blog.humbird.libra.cli.Constants;
//import cd.blog.humbird.libra.util.EncodeUtils;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.RetryNTimes;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by david on 2018/7/11.
// */
//public class ZKCli {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ZKCli.class);
//    private CuratorFramework client;
//
//    public ZKCli(String servers) {
//        this(servers, null);
//    }
//
//    public ZKCli(String servers, String namespace) {
//        try {
//            this.client = CuratorFrameworkFactory.builder()
//                    .connectString(servers)
//                    .retryPolicy(new RetryNTimes(1, 1000))
//                    .sessionTimeoutMs(60000)
//                    .connectionTimeoutMs(30000)
//                    .namespace(namespace)
//                    .build();
//            this.client.getConnectionStateListenable().addListener((cli, newState) -> {
//                String conn = cli.getZookeeperClient().getCurrentConnectionString();
//                LOGGER.info("libra zookeeper {} state: {}", conn, newState);
//            });
//        } catch (Exception e) {
//            LOGGER.error("failed to initialize zookeeper client", e);
//            throw e;
//        }
//    }
//
//    public void start() throws Exception {
//        try {
//            this.client.start();
//        } catch (Exception e) {
//            LOGGER.error("failed to initialize zookeeper client", e);
//            throw e;
//        }
//    }
//
//    public boolean exists(String path) {
//        try {
//            return client.checkExists().forPath(path) != null;
//        } catch (Exception e) {
//            LOGGER.warn("zk checkExists exception. msg:{}", e.getMessage());
//        }
//        return false;
//    }
//
//    public boolean existsWatched(String path) throws Exception {
//        try {
//            return client.checkExists().watched().forPath(path) != null;
//        } catch (Exception e) {
//            LOGGER.warn("zk checkExists watched exception. msg:{}", e.getMessage());
//        }
//        return false;
//    }
//
//
//    public String get(String path) {
//        try {
//            return new String(client.getData().forPath(path), Constants.CHARSET);
//        } catch (Exception e) {
//            LOGGER.warn("failed get path:{},exception:", path, e);
//        }
//        return null;
//    }
//
//    public String get(String path, Stat stat) throws Exception {
//        try {
//            return new String(client.getData().storingStatIn(stat).forPath(path), Constants.CHARSET);
//        } catch (Exception e) {
//            LOGGER.error("failed to get path:{} stat:{},exception:", path, stat, e);
//        }
//        return null;
//    }
//
//    public String getWatched(String path) throws Exception {
//        try {
//            return new String(client.getData().watched().forPath(path), Constants.CHARSET);
//        } catch (Exception e) {
//            LOGGER.warn("failed to get path:{} watched,exception:", path, e);
//        }
//        return null;
//    }
//
//    public List<String> getChildren(String path) throws Exception {
//        try {
//            return client.getChildren().forPath(path);
//        } catch (Exception e) {
//            LOGGER.warn("failed to get children path:{},exception:", path, e);
//        }
//        return Collections.emptyList();
//    }
//
//    public void create(String path, String data) throws Exception {
//        try {
//            client.create().creatingParentsIfNeeded().forPath(path, data.getBytes(EncodeUtils.CHARSET));
//        } catch (Exception e) {
//            LOGGER.error("failed to create path:{},data:{},exception:", path, data, e);
//            throw e;
//        }
//    }
//
//    public void create(String path, byte[] data) throws Exception {
//        try {
//            client.create().creatingParentsIfNeeded().forPath(path, data);
//        } catch (Exception e) {
//            LOGGER.error("failed to create path:{},data:{},exception:", path, data, e);
//            throw e;
//        }
//    }
//
//    public void set(String path, String data) throws Exception {
//        try {
//            client.setData().forPath(path, data.getBytes(EncodeUtils.CHARSET));
//        } catch (Exception e) {
//            LOGGER.error("failed to set path:{},data{},exception:", path, data, e);
//            throw e;
//        }
//    }
//
//    public void set(String path, byte[] data) throws Exception {
//        try {
//            client.setData().forPath(path, data);
//        } catch (Exception e) {
//            LOGGER.error("failed to set path:{},data{},exception:", path, data, e);
//            throw e;
//        }
//    }
//
//    public void createOrSet(String path, String data) throws Exception {
//        if (exists(path)) {
//            set(path, data);
//        } else {
//            create(path, data);
//        }
//    }
//
//    public void delete(String path) throws Exception {
//        try {
//            client.delete().forPath(path);
//        } catch (Exception e) {
//            LOGGER.error("failed to delete path:{},exception:", path, e);
//            throw e;
//        }
//    }
//
//    public void deleteAndChildren(String path) throws Exception {
//        try {
//            client.delete().deletingChildrenIfNeeded().forPath(path);
//        } catch (Exception e) {
//            LOGGER.error("failed to delete path:{},exception:", path, e);
//            throw e;
//        }
//    }
//
//    public void close() {
//        client.close();
//    }
//}
