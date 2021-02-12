package cd.blog.humbird.libra.model.vo;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by david on 2018/7/13.
 */
public class OpLogCriteria extends BaseVO {

    private Integer opTypeStart;
    private Integer opTypeEnd;

    private Long projectId;
    private Long envId;

    private Long userId;
    private Date from = DateUtils.addDays(Calendar.getInstance().getTime(), -7);
    private Date to;
    private String content;

    public Integer getOpTypeStart() {
        return opTypeStart;
    }

    public void setOpTypeStart(Integer opTypeStart) {
        this.opTypeStart = opTypeStart;
    }

    public Integer getOpTypeEnd() {
        return opTypeEnd;
    }

    public void setOpTypeEnd(Integer opTypeEnd) {
        this.opTypeEnd = opTypeEnd;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
