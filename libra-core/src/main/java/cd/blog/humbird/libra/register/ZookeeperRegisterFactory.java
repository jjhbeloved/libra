package cd.blog.humbird.libra.register;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.exception.RegisterException;
import org.springframework.stereotype.Component;

/**
 * @author david
 * @since created by on 18/7/12 02:56
 */
@Component
public class ZookeeperRegisterFactory implements RegisterFactory {

    @Override
    public Register createRegister(Environment environment) throws Exception {
        String servers = environment.getIps();
        Register register = new ZookeeperRegister(servers);
        try {
            register.init();
            return register;
        } catch (Exception e) {
            throw new RegisterException("Create config's zookeeper register service failed.", e);
        }
    }
}
