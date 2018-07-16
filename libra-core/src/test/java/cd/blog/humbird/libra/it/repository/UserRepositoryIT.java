package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.entity.User;
import cd.blog.humbird.libra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by david on 2018/7/16.
 */
public class UserRepositoryIT extends BaseIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void t1() {
        User user = new User();
        user.setLoginName("libra");
        user.setPassword("123");
        user.setName("david");
        user.setEmail("x@gmail.com");
        user.setAdmin(0);
        user.setLocked(0);
        user.setStatus(0);
        long id = userRepository.create(user);
        User u = userRepository.findById(id);
        assertThat(u.getLoginName()).isEqualTo(user.getLoginName());
        userRepository.delete(id);
    }
}
