package cd.blog.humbird.libra.service.impl;

import cd.blog.humbird.libra.common.constant.Parameter;
import cd.blog.humbird.libra.model.po.UserPO;
import cd.blog.humbird.libra.exception.UserLockedException;
import cd.blog.humbird.libra.exception.UserNotFoundException;
import cd.blog.humbird.libra.exception.IncorrectPasswdException;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.repository.UserRepository;
import cd.blog.humbird.libra.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by david on 2018/7/16.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Override
	public PageInfo<UserPO> listUsers(UserCriteria user, Integer pageNum, Integer pageSize) {
		return userRepository.getUsers(
				user,
				Optional.ofNullable(pageNum).orElse(Parameter.DEFAULT_PAGE_NUM),
				Optional.ofNullable(pageSize).orElse(Parameter.DEFAULT_PAGE_SIZE)
		);
	}

	@Override
	public List<UserPO> listUsersByNameOrLoginName(String name, Boolean includeAdmin) {
		return userMapper.findByNameOrLoginNameLike(
				name,
				Optional.ofNullable(includeAdmin).orElse(false)
		);
	}

	@Override
	public UserPO getById(Long id) {
		if (id == null) {
			return null;
		}
		return userRepository.findById(id);
	}

	// todo 这里直接对 entity 赋值有可能直接修改了cache
	@Override
	public UserPO getByIdWithoudPassword(Long id) {
		UserPO userPO = getById(id);
		if (userPO == null) {
			return null;
		}
		UserPO clone = new UserPO();
		BeanUtils.copyProperties(userPO, clone);
		clone.setPassword(null);
		return clone;
	}

	@Override
	public UserPO getByLoginName(String loginName) {
		return userRepository.findByName(loginName);
	}

	/**
	 * 登录
	 *
	 * @param loginName 帐号
	 * @param password  密码
	 * @return 登录用户信息
	 */
	@Override
	public UserPO login(String loginName, String password) {
		UserPO userPO = userRepository.findByName(loginName);
		if (userPO == null) {
			throw new UserNotFoundException(loginName);
		}
		if (userPO.getLocked() == 1) {
			throw new UserLockedException(loginName + "is be locked.");
		}
		if (userPO.getPassword() == null) {
			throw new UserNotFoundException(loginName);
		}
		if (!StringUtils.equals(password, userPO.getPassword())) {
			throw new IncorrectPasswdException(loginName);
		}
		return userPO;
	}

	@Override
	public Long insert(UserPO userPO) {
		return userRepository.create(userPO);
	}

	@Override
	public void update(UserPO userPO) {
		if (userPO == null || userPO.getId() == null) {
			return;
		}
		long id = userPO.getId();
		UserPO prvUserPO = userMapper.findById(id);
		userRepository.update(prvUserPO, userPO);
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		UserPO userPO = userMapper.findById(id);
		userRepository.delete(userPO);
	}

}
