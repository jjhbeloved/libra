package cd.blog.humbird.libra.cli.config.zk;

import cd.blog.humbird.libra.cli.config.ConfigValue;

/**
 * @author david
 * @since created by on 18/7/25 01:43
 */
public class ZKValue extends ConfigValue {

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
