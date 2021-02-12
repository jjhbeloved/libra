package cd.blog.humbird.libra.cli.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 */
public class ConfigValue implements Serializable {

    private String val;

    private String version;

    public ConfigValue() {
    }

    public ConfigValue(String val, String version) {
        this.val = val;
        this.version = version;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
