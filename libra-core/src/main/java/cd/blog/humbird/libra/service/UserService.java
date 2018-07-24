package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.entity.OpLog;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.exception.UserLockedException;
import cd.blog.humbird.libra.exception.UserNotFoundException;
import cd.blog.humbird.libra.exception.IncorrectPasswdException;
import cd.blog.humbird.libra.mapper.UserMapper;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.repository.UserRepository;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by david on 2018/7/16.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public PageInfo<User> findUsers(UserCriteria user, int pageNum, int pageSize) {
        return userRepository.getUsers(user, pageNum, pageSize);
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }

    // todo 这里直接对 entity 赋值有可能直接修改了cache
    public User findNoPwdById(long id) {
        User user = findById(id);
        if (user == null) {
            return null;
        }
        User clone = new User();
        BeanUtils.copyProperties(user, clone);
        clone.setPassword(null);
        return clone;
    }

    public User findByLoginName(String loginName) {
        return userRepository.findByName(loginName);
    }

    public List<User> findByNameOrLoginName(String name, boolean includeAdmin) {
        return userMapper.findByNameOrLoginNameLike(name, includeAdmin);
    }

    /**
     * 登录
     *
     * @param loginName 帐号
     * @param password  密码
     * @return 登录用户信息
     */
    public User login(String loginName, String password) {
        User user = userRepository.findByName(loginName);
        if (user == null) {
            throw new UserNotFoundException(loginName);
        }
        if (user.getLocked() == 1) {
            throw new UserLockedException(loginName + "is be locked.");
        }
        if (user.getPassword() == null) {
            throw new UserNotFoundException(loginName);
        }
        if (!StringUtils.equals(password, user.getPassword())) {
            throw new IncorrectPasswdException(loginName);
        }
        return user;
    }

    public long create(User user) {
        return userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(long id) {
        userRepository.delete(id);
    }

}
