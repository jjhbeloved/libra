package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.cli.BaseUT;
import cd.blog.humbird.libra.common.util.JsonUtils;
import cd.blog.humbird.libra.common.util.ZKUtils;
import cd.blog.humbird.libra.common.zk.ZKCli;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.testng.annotations.Test;

/**
 * @author david
 * @since created by on 18/7/25 03:12
 */
public class ZKConfigLoaderUT extends BaseUT {

    @Test
    public void watch() throws Exception {
        CuratorFramework client = ZKUtils.createCuratorCli("zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181");
        ZKCli zkCli = new ZKCli(client);
        client.getCuratorListenable().addListener((cli, event) -> {
            if (event.getType() == CuratorEventType.WATCHED) {
                System.out.println(event.toString());
            }
            WatchedEvent watchedEvent = event.getWatchedEvent();
            System.out.println(JsonUtils.toJson(watchedEvent));
        });
        client.start();
        while (true) {
            Thread.sleep(2000);
        }
    }

    @Test
    public void trigger() throws Exception {
        CuratorFramework client = ZKUtils.createCuratorCli("zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181");
        ZKCli zkCli = new ZKCli(client);
        client.getCuratorListenable().addListener((cli, event) -> {
            if (event.getType() == CuratorEventType.WATCHED) {
                System.out.println(event.toString());
            }
            WatchedEvent watchedEvent = event.getWatchedEvent();
            if (watchedEvent.getPath() != null) {
                System.out.println(JsonUtils.toJson(watchedEvent));
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeCreated) {
                    System.out.println(zkCli.getWatched(watchedEvent.getPath()));
                } else {
                    System.out.println(zkCli.getWatched(watchedEvent.getPath()));
                }
            }
        });
        client.start();

        String path = "/xxx/yyy";

        if (!zkCli.exists(path)) {
            zkCli.createOrSet(path, "hi".getBytes());
            System.out.println(zkCli.getWatched(path));
        } else {
            System.out.println(zkCli.getWatched(path));
            zkCli.deleteAndChildren(path);
        }
        while (true) {
            Thread.sleep(2000);
        }
    }

}