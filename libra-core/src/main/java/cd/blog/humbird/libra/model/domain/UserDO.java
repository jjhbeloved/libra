package cd.blog.humbird.libra.model.domain;

import cd.blog.humbird.libra.model.vo.BaseVO;

/**
 * Created by david on 2018/7/13.
 */
public class UserDO extends BaseVO {

    private long id;
    private String name;
    private String frIp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrIp() {
        return frIp;
    }

    public void setFrIp(String frIp) {
        this.frIp = frIp;
    }
}
