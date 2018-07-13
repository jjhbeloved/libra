package cd.blog.humbird.libra.it.service;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.repository.RegisterRepository;
import cd.blog.humbird.libra.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/12.
 */
public class EnvironmentServiceIT extends BaseIT {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void init() {
        int count = registerRepository.getRegisterEnvIds().size();
        long id = environmentService.create("xb", "XB", "zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181", 0);
        List<Environment> environment_1 = environmentService.findAll();
        Set<Long> envIds_1 = registerRepository.getRegisterEnvIds();
        envIds_1.forEach(System.out::println);
        assertThat(envIds_1.size()).isEqualTo(count);
        environmentService.delete(id);
        registerRepository.getRegisterEnvIds().forEach(System.out::println);
        assertThat(envIds_1.size()).isEqualTo(count);
    }
}
