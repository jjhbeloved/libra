package cd.blog.humbird.libra.it.manager;

import cd.blog.humbird.libra.BaseIT;
import cd.blog.humbird.libra.manager.EnvironmentManager;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import cd.blog.humbird.libra.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by david on 2018/7/12.
 */
public class EnvironmentManagerIT extends BaseIT {

    @Autowired
    private EnvironmentManager environmentManager;

    @Test
    public void init() {
    }
}
