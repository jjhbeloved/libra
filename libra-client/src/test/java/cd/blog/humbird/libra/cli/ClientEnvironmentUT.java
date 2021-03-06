package cd.blog.humbird.libra.cli;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

/**
 * @author david
 * @since created by on 18/7/5 01:31
 */
public class ClientEnvironmentUT extends BaseUT {

    @Test
    public void loadAppEnv() {
        Properties properties = ClientEnvironment.loadAppEnv();
        Assert.assertTrue(properties.size() > 0);
        Assert.assertTrue(ClientEnvironment.isReadCache());
        Assert.assertEquals(ClientEnvironment.getSyncInterval(), 1800000);
    }
}