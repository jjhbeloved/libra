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

    @Test(description = "创建环境, 但是不刷新服务端本地缓存")
    public void t1() {
        System.out.println("====================================================");
        int count = registerRepository.getRegisterEnvIds().size();
        long id = environmentService.create("xb", "XB", "zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181", 0);
        System.out.println("====================================================");
        List<Environment> environment_1 = environmentService.findAll();
        Set<Long> envIds_1 = registerRepository.getRegisterEnvIds();
        envIds_1.forEach(System.out::println);
        assertThat(envIds_1.size()).isEqualTo(count);
        environmentService.delete(id);
        System.out.println("====================================================");
        registerRepository.getRegisterEnvIds().forEach(System.out::println);
        assertThat(envIds_1.size()).isEqualTo(count);
        System.out.println("====================================================");
    }

    @Test(description = "创建环境并且刷新服务端本地缓存")
    public void t2() {
        System.out.println("====================================================");
        int count = registerRepository.getRegisterEnvIds().size();
        int size = environmentService.findAll().size();
        long id = environmentService.create("xb", "XB", "zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181", 0);
        System.out.println("====================================================");
        List<Environment> environment_1 = environmentService.findAllAndrefresh();
        Set<Long> envIds_1 = registerRepository.getRegisterEnvIds();
        assertThat(envIds_1.size()).isEqualTo(count + 1);
        assertThat(environment_1.size()).isEqualTo(size + 1);
        environmentService.delete(id);
        System.out.println("====================================================");
        List<Environment> environment_2 = environmentService.findAll();
        assertThat(environment_2.size()).isEqualTo(size);
        List<Environment> environment_3 = environmentService.findAllAndrefresh();
        Set<Long> envIds_3 = registerRepository.getRegisterEnvIds();
        assertThat(envIds_3.size()).isEqualTo(count);
        System.out.println("====================================================");
    }

    @Test(description = "更新环境并且刷新服务端本地缓存")
    public void t3() {
        int count = registerRepository.getRegisterEnvIds().size();
        int size = environmentService.findAll().size();

        String name = "xb";
        long id = environmentService.create(name, "XB", "zk1.dev.pajkdc.com:2181,zk2.dev.pajkdc.com:2181,zk3.dev.pajkdc.com:2181", 0);
        Environment env = environmentService.findByName(name);
        environmentService.update(env.getId(), env.getIps(), 1);
        List<Environment> environment_1 = environmentService.findAllAndrefresh();
        int count_2 = registerRepository.getRegisterEnvIds().size();
        assertThat(count_2).isEqualTo(count);
        assertThat(environment_1.size()).isEqualTo(size + 1);
        environmentService.delete(id);
    }
}
