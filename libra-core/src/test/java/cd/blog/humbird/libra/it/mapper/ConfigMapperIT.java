package cd.blog.humbird.libra.it.mapper;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.mapper.ConfigMapper;
import cd.blog.humbird.libra.model.vo.ConfigCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/19.
 */
public class ConfigMapperIT extends BaseIT {

    @Autowired
    private ConfigMapper configMapper;

    @Test
    public void t1() {
        Config config = new Config();
        config.setModifier("david");
        config.setCreator("david");
        config.setCreatorId(123L);
        config.setProjectId(123456L);
        config.setPri(1);
        config.setKey("xmmomx");
        config.setDesc("xxxxxx");
        config.setType(1);
        configMapper.insert(config);
        long id = config.getId();
        try {
            assertThat(configMapper.findConfigById(id)).isNotNull();
            assertThat(configMapper.findConfigByKey(config.getKey())).isNotEmpty();
            assertThat(configMapper.findByKeyAndProjectId("mo", config.getProjectId())).isNotNull();
            assertThat(configMapper.findConfigByKeyParttern("mo")).isNotEmpty();
            assertThat(configMapper.findConfigByCreatorId(config.getCreatorId())).isNotEmpty();
            assertThat(configMapper.findConfigByProjectId(config.getProjectId())).isNotEmpty();

            ConfigCriteria configCriteria = new ConfigCriteria();
            configCriteria.setKey("mo");
            configCriteria.setProjectId(config.getProjectId());
            assertThat(configMapper.findConfigs(configCriteria)).isNotEmpty();

            config.setDesc("xbx");
            configMapper.update(config);
            assertThat(configMapper.findConfigById(id).getDesc()).isEqualTo(config.getDesc());
        } finally {
            configMapper.delete(config.getId());
        }
    }
}
