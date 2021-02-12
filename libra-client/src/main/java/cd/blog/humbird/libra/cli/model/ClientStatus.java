package cd.blog.humbird.libra.cli.model;

import cd.blog.humbird.libra.cli.ClientEnv;
import cd.blog.humbird.libra.common.util.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author david
 * @since created by on 18/7/25 23:25
 */
public class ClientStatus implements Serializable {

    private String host = SystemUtils.getIPv4Host();
    private String pid = SystemUtils.getPID();
    private String appName = ClientEnv.getAppName();
    private String appVer = ClientEnv.getAppVersion();
    private String appEnv = ClientEnv.getEnv().toUpperCase();
    private long startTime = ClientEnv.getStartTime();
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
