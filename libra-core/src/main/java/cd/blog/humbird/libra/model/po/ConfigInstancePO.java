package cd.blog.humbird.libra.model.po;

/**
 * @author david
 * @since created by on 2018/7/19 23:29
 */
public class ConfigInstancePO extends BasePO {

    private long configId;

    private long envId;

    private String desc;

    private String value;

    private String context;

    private String contextmd5;

    private long creatorId;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public long getEnvId() {
        return envId;
    }

    public void setEnvId(long envId) {
        this.envId = envId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContextmd5() {
        return contextmd5;
    }

    public void setContextmd5(String contextmd5) {
        this.contextmd5 = contextmd5;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}
