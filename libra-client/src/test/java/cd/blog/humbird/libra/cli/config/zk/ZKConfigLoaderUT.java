package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.cli.BaseUT;
import cd.blog.humbird.libra.common.util.ZKUtil;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.zookeeper.WatchedEvent;
import org.testng.annotations.Test;

/**
 * @author david
 * @since created by on 18/7/25 03:12
 */
public class ZKConfigLoaderUT extends BaseUT {


    @Test
    public void watch() throws Exception {
        CuratorFramework client = ZKUtil.createCuratorCli("0.0.0.0:2181");
        client.getCuratorListenable().addListener((cli, event) -> {
            if (event.getType() == CuratorEventType.WATCHED) {
                System.out.println(event.toString());
            }
            WatchedEvent watchedEvent = event.getWatchedEvent();
            System.out.println(JSON.toJSONString(watchedEvent));
        });
        client.start();
        while (true) {
            Thread.sleep(2000);
        }
    }

    @Test
    public void trigger() throws Exception {
        CuratorFramework client = ZKUtil.createCuratorCli("0.0.0.0:2181");
        client.start();

        String path = "/xxx/yyy";
        if (client.checkExists().forPath(path) == null) {
            client.create().creatingParentsIfNeeded().forPath(path, "hi".getBytes());
        } else {
            System.out.println(new String(client.getData().forPath(path)));
            client.delete().forPath(path);
        }
        client.close();
    }

}