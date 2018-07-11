package cd.blog.humbird.libra.it.register;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.register.ZookeeperRegister;
import cd.blog.humbird.libra.util.EncodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by david on 2018/7/11.
 */
public class ZookeeperRegisterIT extends BaseIT {

    @Autowired
    private ZookeeperRegister zookeeperRegister;

    @Test
    public void registerContextAndPush() {
        String key = "david";
        String value = "hello";
        String v2 = "xz";
        String v3 = "v3";
        zookeeperRegister.registerContextValue(key, value);
        assertThat(zookeeperRegister.get(key + "/CONTEXTVALUE")).isEqualTo(value);

        zookeeperRegister.registerAndPushContextValue(key, v2);
        zookeeperRegister.registerDefaultValue(key, v3);
        assertThat(zookeeperRegister.get(key)).isEqualTo(v3);
        assertThat(zookeeperRegister.get(key + "/CONTEXTVALUE")).isEqualTo(v2);
        String rs0 = zookeeperRegister.get(key + "/TIMESTAMP");
        assertThat(rs0).isNotBlank();
        System.out.println(rs0);

        zookeeperRegister.unregister(key);
        assertThat(zookeeperRegister.get(key)).isNull();
    }

    @Test
    public void registerGroupAndPush() {
        String key = "david";
        String group = "gp";
        String value = "hello";
        String v2 = "xz";
        zookeeperRegister.registerGroupValue(key, group, value);
        assertThat(zookeeperRegister.get(key, group)).isEqualTo(value);

        zookeeperRegister.registerAndPushGroupValue(key, group, v2);
        assertThat(zookeeperRegister.get(key, group)).isEqualTo(v2);
        String rs0 = zookeeperRegister.get(key, group + "/TIMESTAMP");
        assertThat(rs0).isNotBlank();
        System.out.println(rs0);

        zookeeperRegister.unregister(key);
        assertThat(zookeeperRegister.get(key)).isNull();
    }

    @Test
    public void getAddressAndStat() {
        String key = "david";
        String group = "gp";
        String value = "hello";
        zookeeperRegister.registerGroupValue(key, group, value);
        assertThat(zookeeperRegister.get(key, group)).isEqualTo(value);

        assertThat(zookeeperRegister.getAddresses()).isNotBlank();
        String version = zookeeperRegister.getRemoteDataVersion(key);
        assertThat(version).isNotBlank();
        System.out.println(version);
        zookeeperRegister.unregister(key);
        assertThat(zookeeperRegister.get(key)).isNull();
    }
}
