package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by david on 2018/7/20.
 */
public class ConfigRepositoryIT extends BaseIT {

    @Autowired
    private ConfigRepository configRepository;

    @Test
    public void t1() {
        Config config = new Config();
        config.setProjectId(123456);
        config.setKey("hello.world");
        config.setPri(1);
        config.setType(1);
        config.setDesc("HelloWorld.");
        long id = configRepository.createConfig(config);
        try {
            System.out.println(configRepository.findConfigById(id).toString());
        } finally {
            configRepository.deleteConfig(id);
        }
    }
}
