package cd.blog.humbird.libra.entity;

/**
 * Created by david on 2018/7/12.
 */
public class SystemSetting extends BaseEntity {

    private String key;

    private String value;

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
}
