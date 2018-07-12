package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import cd.blog.humbird.libra.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/11.
 */
public class EnvironmentRepositoryIT extends BaseIT {

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void init() {
        registerRepository.getRegisterEnvIds().forEach(aLong -> System.out.println());
    }

}
