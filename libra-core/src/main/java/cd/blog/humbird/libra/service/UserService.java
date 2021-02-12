package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.model.po.UserPO;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author david
 * @since created by on 18/7/30 23:35
 */
public interface UserService {

	PageInfo<UserPO> listUsers(UserCriteria user, Integer pageNum, Integer pageSize);

	List<UserPO> listUsersByNameOrLoginName(String name, Boolean includeAdmin);

	UserPO getById(Long id);

	UserPO getByIdWithoudPassword(Long id);

	UserPO getByLoginName(String loginName);

	UserPO login(String loginName, String password);

	Long insert(UserPO userPO);

	void update(UserPO userPO);

	void delete(Long id);
}
