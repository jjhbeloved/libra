package cd.blog.humbird.libra.it.service;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.exception.IncorrectPasswdException;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/18.
 */
public class UserServiceIT extends BaseIT {

    @Autowired
    private UserService userService;

    public User create(int i) {
        User user = new User();
        user.setLoginName("libra" + i);
        user.setPassword(DigestUtils.md5Hex("123".toUpperCase()));
        user.setName("david" + i);
        user.setEmail("x@gmail.com");
        user.setAdmin(0);
        user.setLocked(0);
        user.setStatus(0);
        return user;
    }

    @Test
    public void t1() {
        User user = create(1);
        long id = userService.create(user);
        try {
            User u = userService.findById(id);
            assertThat(u.getPassword()).isEqualTo(user.getPassword());
            assertThat(userService.findNoPwdById(id).getPassword()).isNull();
            assertThat(userService.findByNameOrLoginName(user.getName(), false)).isNotEmpty();
            assertThat(userService.findByLoginName(user.getLoginName())).isNotNull();
            assertThat(userService.findByLoginName("##--##")).isNull();
            assertThat(userService.login(u.getLoginName(), u.getPassword())).isNotNull();
            u.setPassword(DigestUtils.md5Hex("Xb"));
            userService.update(u);
            try {
                userService.login(u.getLoginName(), u.getPassword());
            } catch (Exception e) {
                assertThat(e).isInstanceOf(IncorrectPasswdException.class);
            }
            UserCriteria criteria = new UserCriteria();
            criteria.setName(user.getName());
            PageInfo<User> pageInfo = userService.findUsers(criteria, 1, 5);
            assertThat(pageInfo.getList()).isNotEmpty();
        } finally {
            userService.delete(id);
        }
    }
}
