package cd.blog.humbird.libra.model.vo;

/**
 * Created by david on 2018/7/19.
 */
public class ConfigCriteria extends BaseVO {

    private String key;

    private Long projectId;

    private Long creatorId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
