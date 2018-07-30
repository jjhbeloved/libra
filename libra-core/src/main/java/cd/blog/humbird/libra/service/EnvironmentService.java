package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.model.po.EnvironmentPO;

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
	List<EnvironmentPO> listAll();

	/**
	 * 获取全部环境信息并且刷新
	 *
	 * @return 环境列表
	 */
	List<EnvironmentPO> listAllAndRefresh();

	/**
	 * 根据环境id获取环境信息
	 *
	 * @param id 环境ID
	 * @return 环境
	 */
	EnvironmentPO getById(Long id);

	/**
	 * 根据环境名获取环境信息
	 *
	 * @param name 环境名
	 * @return 环境
	 */
	EnvironmentPO getByName(String name);

	/**
	 * 新增环境信息
	 *
	 * @param name   环境名
	 * @param label  环境标签
	 * @param ips    环境地址
	 * @param status 状态(null=0)
	 * @return 新增的环境ID
	 */
	Long insert(String name, String label, String ips, Integer status);

	/**
	 * 更新环境信息
	 *
	 * @param id     更新的环境id
	 * @param ips    更新的环境地址
	 * @param status 更新的环境状态(null=0)
	 */
	void update(Long id, String ips, Integer status);

	/**
	 * 删除环境信息
	 *
	 * @param id 环境ID
	 */
	void delete(Long id);
}
