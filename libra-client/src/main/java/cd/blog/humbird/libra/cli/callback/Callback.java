package cd.blog.humbird.libra.cli.callback;

import cd.blog.humbird.libra.common.zk.ZKCli;

/**
 * @author david
 * @since created by on 18/7/25 22:16
 */
public interface Callback {

    void setZKCli(ZKCli zkCli);

    /**
     * 创建回调信息
     *
     * @param path 地址
     * @param val  信息
     */
    void create(String path, String val);

    /**
     * 重试创建回调信息
     */
    void reCreate();

    /**
     * 删除回调信息
     *
     * @param path 地址
     */
    void delete(String path);
}
