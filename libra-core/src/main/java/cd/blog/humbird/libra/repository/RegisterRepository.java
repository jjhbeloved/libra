package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.register.Register;
import cd.blog.humbird.libra.register.RegisterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author david
 * @since created by on 18/7/12 03:29
 */
@Repository
public class RegisterRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterRepository.class);

    private static final ConcurrentMap<Long, Register> registers = new ConcurrentHashMap<>();

    @Resource(name = "zookeeperRegisterFactory")
    private RegisterFactory registerFactory;

    @Autowired
    private EnvironmentRepository environmentRepository;

    /**
     * 根据环境创建注册器
     *
     * @param environment
     * @return
     */
    public Register createRegister(Environment environment) {
        if (environment != null) {
            try {
                Register register = registerFactory.createRegister(environment);
                return setRegister(environment.getId(), register);
            } catch (Exception e) {
                LOGGER.error("Create config register service[env={},ips={}] failed.exception:{}",
                        environment.getLabel(), environment.getIps(), e.getMessage());
            }
        }
        return null;
    }

    public Register getRegister(long envId) {
        return registers.get(envId);
    }

    /**
     * 获取所有的注册环境信息
     *
     * @return
     */
    public Set<Long> getRegisterEnvIds() {
        return registers.keySet();
    }

    public void removeRegister(long envId) {
        Register register = registers.remove(envId);
        if (register != null) {
            destoryRegister(register);
        }
    }

    private Register setRegister(long envId, Register register) {
        Register oldRegister = registers.putIfAbsent(envId, register);
        if (oldRegister == null) {
            return register;
        } else {
            destoryRegister(register);
            return oldRegister;
        }
    }

    private void destoryRegister(Register register) {
        register.destroy();
    }
}
