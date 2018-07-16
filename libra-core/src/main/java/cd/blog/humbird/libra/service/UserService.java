package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.exception.UserException;
import cd.blog.humbird.libra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by david on 2018/7/16.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // todo 这里直接对 entity 赋值有可能直接修改了DB
    public User findNoPwdById(long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
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
            throw new UserException(loginName);
        }
        if (user.getLocked() == 1) {
            throw new UserException(loginName + "is be locked.");
        }
        return user;
    }

}
