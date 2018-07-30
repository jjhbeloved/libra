package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.entity.Environment;

import java.util.List;

/**
 * Created by david on 2018/7/30.
 */
public interface EnvironmentService {

    /**
     * 获取全部环境信息
     *
     * @return 环境列表
     */
    List<Environment> listAll();

    /**
     * 获取全部环境信息并且刷新
     *
     * @return 环境列表
     */
    List<Environment> listAllAndRrefresh();

    /**
     * 根据环境id获取环境信息
     *
     * @param id 环境ID
     * @return 环境
     */
    Environment getById(long id);

    /**
     * 根据环境名获取环境信息
     *
     * @param name 环境名
     * @return 环境
     */
    Environment getByName(String name);

    /**
     * 新增环境信息
     *
     * @param name   环境名
     * @param label  环境标签
     * @param ips    环境地址
     * @param status 状态
     * @return 新增的环境ID
     */
    long insert(String name, String label, String ips, int status);

    /**
     * 更新环境信息
     *
     * @param id     更新的环境id
     * @param ips    更新的环境地址
     * @param status 更新的环境状态
     */
    void update(long id, String ips, int status);

    /**
     * 删除环境信息
     *
     * @param id 环境ID
     */
    void delete(long id);
}
