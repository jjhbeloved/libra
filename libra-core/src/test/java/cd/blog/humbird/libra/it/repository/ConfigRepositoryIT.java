package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.model.po.ConfigPO;
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
        ConfigPO configPO = new ConfigPO();
        configPO.setProjectId(123456);
        configPO.setKey("hello.world");
        configPO.setPri(1);
        configPO.setType(1);
        configPO.setDesc("HelloWorld.");
        long id = configRepository.createConfig(configPO);
        try {
            System.out.println(configRepository.findConfigById(id).toString());
        } finally {
            configRepository.deleteConfig(id);
        }
    }
}
