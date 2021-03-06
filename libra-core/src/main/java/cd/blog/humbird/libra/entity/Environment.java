package cd.blog.humbird.libra.entity;

/**
 * Created by david on 2018/7/11.
 */
public class Environment extends BaseEntity {

    private String name;
    private String label;
    private String ips;
    // 0启动/1停止
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
