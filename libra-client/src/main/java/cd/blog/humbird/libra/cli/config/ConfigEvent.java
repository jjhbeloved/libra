package cd.blog.humbird.libra.cli.config;

/**
 * @author david
 * @since created by on 18/7/25 00:33
 */
public class ConfigEvent {

    private String key;
    private String value;
    private String version;

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
}
