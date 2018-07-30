package cd.blog.humbird.libra.it.service;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.model.po.UserPO;
import cd.blog.humbird.libra.exception.IncorrectPasswdException;
import cd.blog.humbird.libra.model.vo.UserCriteria;
import cd.blog.humbird.libra.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/18.
 */
public class UserServiceImplIT extends BaseIT {

    @Autowired
    private UserServiceImpl userServiceImpl;

    public UserPO create(int i) {
        UserPO userPO = new UserPO();
        userPO.setLoginName("libra" + i);
        userPO.setPassword(DigestUtils.md5Hex("123".toUpperCase()));
        userPO.setName("david" + i);
        userPO.setEmail("x@gmail.com");
        userPO.setAdmin(0);
        userPO.setLocked(0);
        userPO.setStatus(0);
        return userPO;
    }

    @Test
    public void t1() {
        UserPO userPO = create(1);
        long id = userServiceImpl.insert(userPO);
        try {
            UserPO u = userServiceImpl.getById(id);
            assertThat(u.getPassword()).isEqualTo(userPO.getPassword());
            assertThat(userServiceImpl.getByIdWithoudPassword(id).getPassword()).isNull();
            assertThat(userServiceImpl.listUsersByNameOrLoginName(userPO.getName(), false)).isNotEmpty();
            assertThat(userServiceImpl.getByLoginName(userPO.getLoginName())).isNotNull();
            assertThat(userServiceImpl.getByLoginName("##--##")).isNull();
            assertThat(userServiceImpl.login(u.getLoginName(), u.getPassword())).isNotNull();
            u.setPassword(DigestUtils.md5Hex("Xb"));
            userServiceImpl.update(u);
            try {
                userServiceImpl.login(u.getLoginName(), u.getPassword());
            } catch (Exception e) {
                assertThat(e).isInstanceOf(IncorrectPasswdException.class);
            }
            UserCriteria criteria = new UserCriteria();
            criteria.setName(userPO.getName());
            PageInfo<UserPO> pageInfo = userServiceImpl.listUsers(criteria, 1, 5);
            assertThat(pageInfo.getList()).isNotEmpty();
        } finally {
            userServiceImpl.delete(id);
        }
    }
}
