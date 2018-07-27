package cd.blog.humbird.libra.cli.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author david
 * @since created by on 18/7/25 00:33
 */
public class ConfigEvent {

    private String key;
    // 如果value==null意味着删除
    private String value;
    private String version;

    public ConfigEvent(String key) {
        this(key, null);
    }

    public ConfigEvent(String key, String value) {
        this(key, value, null);
    }

    public ConfigEvent(String key, String value, String version) {
        this.key = key;
        this.value = value;
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isDeleted() {
        return value == null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
