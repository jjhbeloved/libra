package cd.blog.humbird.libra.cli.model;

import cd.blog.humbird.libra.cli.ClientEnvironment;
import cd.blog.humbird.libra.common.util.SystemUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author david
 * @since created by on 18/7/25 23:25
 */
public class ClientStatus {

    private String host = SystemUtil.getIPv4Host();
    private String pid = SystemUtil.getPID();
    private String appName = ClientEnvironment.getAppName();
    private String appVer = ClientEnvironment.getAppVersion();
    private String appEnv = ClientEnvironment.getEnv().toUpperCase();
    private long startTime = ClientEnvironment.getStartTime();
    private long msgTime = System.currentTimeMillis();
    private String tag;
    private String msg;

    public String getHost() {
        return host;
    }

    public String getPid() {
        return pid;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVer() {
        return appVer;
    }

    public String getAppEnv() {
        return appEnv;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
