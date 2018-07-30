package cd.blog.humbird.libra.it.mapper;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.model.po.EnvironmentPO;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/12.
 */
public class EnvironmentMapperIT extends BaseIT {

    @Autowired
    EnvironmentMapper mapper;

    @Value("${zk.url}")
    private String url;

    @Test
    public void db() {
        EnvironmentPO environmentPO = new EnvironmentPO();
        environmentPO.setIps(url);
        environmentPO.setLabel("dev");
        environmentPO.setName("DEV");
        environmentPO.setStatus(0);
        environmentPO.setCreator("david");
        environmentPO.setModifier("david");
        mapper.insert(environmentPO);
        long id = environmentPO.getId();
        EnvironmentPO rs = mapper.findByName(environmentPO.getName());
        rs.setName("xv");
        mapper.update(rs);
        assertThat(mapper.findById(id).getName()).isEqualTo("xv");
        mapper.delete(id);
        assertThat(mapper.findAll()).isEmpty();
    }

}
