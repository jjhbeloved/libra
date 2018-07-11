package cd.blog.humbird.libra.manager;

import cd.blog.humbird.libra.register.Register;
import cd.blog.humbird.libra.register.RegisterFactory;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author david
 * @since created by on 18/7/12 03:29
 */
@Component
public class RegisterManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterManager.class);

    private static ConcurrentMap<Long, Register> registers = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier("zookeeperRegisterFactory")
    private RegisterFactory registerFactory;

    @Autowired
    private EnvironmentRepository environmentRepository;


    public Register getRequiredRegister(long envId) {
        Register register = registers.get(envId);
        if (register != null) {
            return register;
        }
//        Environment environment =
        return null;
    }
}
