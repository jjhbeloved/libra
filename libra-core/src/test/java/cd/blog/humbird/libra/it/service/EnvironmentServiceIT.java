package cd.blog.humbird.libra.it.service;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.repository.mapper.EnviornmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/11.
 */
public class EnvironmentServiceIT extends BaseIT {

    @Autowired
    EnviornmentMapper mapper;

    @Value("${zk.url}")
    private String url;

    @Test
    public void db() {
        Environment environment = new Environment();
        environment.setIps(url);
        environment.setLabel("dev");
        environment.setName("DEV");
        environment.setStatus(0);
        environment.setCreator("david");
        environment.setModifier("david");
        mapper.create(environment);
        long id = environment.getId();
        Environment rs = mapper.findByName(environment.getName());
        rs.setName("xv");
        mapper.update(rs);
        assertThat(mapper.findByID(id).getName()).isEqualTo("xv");
        mapper.delete(id);
        assertThat(mapper.findAll()).isEmpty();

    }
}
