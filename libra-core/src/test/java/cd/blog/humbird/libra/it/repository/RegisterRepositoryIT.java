package cd.blog.humbird.libra.it.repository;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * Created by david on 2018/7/13.
 */
public class RegisterRepositoryIT extends BaseIT {

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void init() {
        Set<Long> ids = registerRepository.getRegisterEnvIds();
        ids.forEach(System.out::println);
    }
}
