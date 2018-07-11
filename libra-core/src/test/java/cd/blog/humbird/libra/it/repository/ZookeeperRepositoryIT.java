package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.repository.ZookeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

/**
 * Created by david on 2018/7/11.
 */
public class ZookeeperRepositoryIT extends BaseIT {

    @Autowired
    private ZookeeperRepository zookeeperRepository;

    @Value("${zk.url}")
    private String url;

    @Test
    public void zk() {
    }
}
