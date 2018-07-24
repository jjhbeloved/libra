package cd.blog.humbird.libra.entity;

import java.util.Date;

/**
 * Created by david on 2018/7/13.
 */
public class OpLog extends BaseEntity {

    private int opType;

    private long opUserId;

    private String opUserIp;

    private Date opTime;

    private Long envId;

    private Long projectId;

    private String content;

    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String key5;
    private String key6;

    public OpLog(int opType, long opUserId, String content) {
        this(opType, opUserId, null, null, null, content);
    }

    public OpLog(int opType, long opUserId, String opUserIp, Long envId, Long projectId, String content) {
        this.opType = opType;
        this.opUserId = opUserId;
        this.opUserIp = opUserIp;
        this.envId = envId;
        this.projectId = projectId;
        this.content = content;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public long getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(long opUserId) {
        this.opUserId = opUserId;
    }

    public String getOpUserIp() {
        return opUserIp;
    }

    public void setOpUserIp(String opUserIp) {
        this.opUserIp = opUserIp;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public String getKey6() {
        return key6;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }
}
