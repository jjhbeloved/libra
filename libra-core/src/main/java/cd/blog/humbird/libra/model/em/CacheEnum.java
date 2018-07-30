package cd.blog.humbird.libra.model.em;

public enum CacheEnum {

    /**
     * 系统信息本地缓存
     */
    SystemLocalCache("systemLocalCache", 600, 10000, "系统短时间缓存"),
    /**
     * 环境信息本地缓存
     */
    EnvLocalCache("envLocalCache", 600, 10000, "环境短时间缓存"),
    /**
     * 团队信息本地缓存
     */
    TeamLocalCache("teamLocalCache", 600, 10000, "团队短时间缓存"),
    /**
     * 配置信息本地缓存
     */
    ConfigLocalCache("configLocalCache", 600, 20000, "配置短时间缓存"),
    /**
     * 用户信息本地缓存
     */
    UserLocalCache("userLocalCache", 1800, 20000, "用户缓存");

    /**
     * @author david
     * @since created by on 2018/7/24 23:29
     */
    private String code;
    private int timeOut;
    private int maxSize;
    private String desc;

    CacheEnum(String code, int timeOut, int maxSize, String desc) {
        this.code = code;
        this.timeOut = timeOut;
        this.maxSize = maxSize;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public String getDesc() {
        return desc;
    }
}