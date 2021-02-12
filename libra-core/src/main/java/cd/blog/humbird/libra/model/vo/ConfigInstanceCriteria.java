package cd.blog.humbird.libra.model.vo;

/**
 * @author david
 * @since created by on 2018/7/20 23:29
 */
public class ConfigInstanceCriteria extends BaseVO {

    private Long configId;

    private Long envId;

    private Long creatorId;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
