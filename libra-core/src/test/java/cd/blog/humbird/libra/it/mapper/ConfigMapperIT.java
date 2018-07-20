package cd.blog.humbird.libra.it.mapper;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.Config;
import cd.blog.humbird.libra.entity.ConfigInstance;
import cd.blog.humbird.libra.mapper.ConfigMapper;
import cd.blog.humbird.libra.model.vo.ConfigCriteria;
import cd.blog.humbird.libra.model.vo.ConfigInstanceCriteria;
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
        configMapper.insertConfig(config);
        long id = config.getId();
        try {
            assertThat(configMapper.findConfigById(id)).isNotNull();
            assertThat(configMapper.findConfigByKey(config.getKey())).isNotEmpty();
            assertThat(configMapper.findConfigByKeyAndProjectId("mo", config.getProjectId())).isNotNull();
            assertThat(configMapper.findConfigByKeyParttern("mo")).isNotEmpty();
            assertThat(configMapper.findConfigByCreatorId(config.getCreatorId())).isNotEmpty();
            assertThat(configMapper.findConfigByProjectId(config.getProjectId())).isNotEmpty();

            ConfigCriteria configCriteria = new ConfigCriteria();
            configCriteria.setKey("mo");
            configCriteria.setProjectId(config.getProjectId());
            assertThat(configMapper.findConfigs(configCriteria)).isNotEmpty();

            config.setDesc("xbx");
            configMapper.updateConfig(config);
            assertThat(configMapper.findConfigById(id).getDesc()).isEqualTo(config.getDesc());
        } finally {
            configMapper.deleteConfig(config.getId());
        }
    }

    @Test
    public void t2() {
        ConfigInstance config = new ConfigInstance();
        config.setModifier("david");
        config.setCreator("david");
        config.setCreatorId(123L);
        config.setDesc("xxxxxx");
        config.setConfigId(30);
        config.setEnvId(30);
        config.setValue("xxxxxxx");
        config.setContext("bmb");
        config.setContextmd5("md5");
        configMapper.insertConfigInstance(config);
        long id = config.getId();
        try {
            assertThat(configMapper.findConfigInstanceById(id)).isNotNull();
            assertThat(configMapper.findConfigInstanceByCreatorId(config.getCreatorId())).isNotEmpty();
            assertThat(configMapper.findConfigInstanceByConfigIdAndEnvId(config.getConfigId(), config.getEnvId())).isNotNull();

            ConfigInstanceCriteria criteria = new ConfigInstanceCriteria();
            criteria.setConfigId(config.getConfigId());
            criteria.setEnvId(config.getEnvId());
            criteria.setCreatorId(config.getCreatorId());
            assertThat(configMapper.findConfigInstances(criteria)).isNotEmpty();

            config.setDesc("xbx");
            configMapper.updateConfigInstance(config);
            assertThat(configMapper.findConfigInstanceById(id).getDesc()).isEqualTo(config.getDesc());
        } finally {
            configMapper.deleteConfigInstance(id);
        }
    }
}
