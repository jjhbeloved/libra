package cd.blog.humbird.libra.it.mapper;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.model.po.ConfigPO;
import cd.blog.humbird.libra.model.po.ConfigInstancePO;
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
        ConfigPO configPO = new ConfigPO();
        configPO.setModifier("david");
        configPO.setCreator("david");
        configPO.setCreatorId(123L);
        configPO.setProjectId(123456L);
        configPO.setPri(1);
        configPO.setKey("xmmomx");
        configPO.setDesc("xxxxxx");
        configPO.setType(1);
        configMapper.insertConfig(configPO);
        long id = configPO.getId();
        try {
            assertThat(configMapper.getConfigById(id)).isNotNull();
            assertThat(configMapper.listConfigsByKey(configPO.getKey())).isNotEmpty();
            assertThat(configMapper.getConfigByKeyAndProjectId("mo", configPO.getProjectId())).isNotNull();
            assertThat(configMapper.listConfigsByKeyParttern("mo")).isNotEmpty();
            assertThat(configMapper.listConfigsByCreatorId(configPO.getCreatorId())).isNotEmpty();
            assertThat(configMapper.listConfigsByProjectId(configPO.getProjectId())).isNotEmpty();

            ConfigCriteria configCriteria = new ConfigCriteria();
            configCriteria.setKey("mo");
            configCriteria.setProjectId(configPO.getProjectId());
            assertThat(configMapper.listConfigs(configCriteria)).isNotEmpty();

            configPO.setDesc("xbx");
            configMapper.updateConfig(configPO);
            assertThat(configMapper.getConfigById(id).getDesc()).isEqualTo(configPO.getDesc());
        } finally {
            configMapper.deleteConfig(configPO.getId());
        }
    }

    @Test
    public void t2() {
        ConfigInstancePO config = new ConfigInstancePO();
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
            assertThat(configMapper.getConfigInstanceById(id)).isNotNull();
            assertThat(configMapper.listConfigInstancesByCreatorId(config.getCreatorId())).isNotEmpty();
            assertThat(configMapper.getConfigInstanceByConfigIdAndEnvId(config.getConfigId(), config.getEnvId())).isNotNull();

            ConfigInstanceCriteria criteria = new ConfigInstanceCriteria();
            criteria.setConfigId(config.getConfigId());
            criteria.setEnvId(config.getEnvId());
            criteria.setCreatorId(config.getCreatorId());
            assertThat(configMapper.listConfigInstances(criteria)).isNotEmpty();

            config.setDesc("xbx");
            configMapper.updateConfigInstance(config);
            assertThat(configMapper.getConfigInstanceById(id).getDesc()).isEqualTo(config.getDesc());
        } finally {
            configMapper.deleteConfigInstance(id);
        }
    }
}
