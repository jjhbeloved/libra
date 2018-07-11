package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.mapper.ZKCli;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

/**
 * Created by david on 2018/7/11.
 */
public class ZKCliIT extends BaseIT {

    private ZKCli zkCli;

    @Value("${zk.url}")
    private String url;

    @Test
    public void zk() {
    }
}
