package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.model.po.EnvironmentPO;
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

    private static final ConcurrentMap<Long, Register> REGISTERS = new ConcurrentHashMap<>();

    @Resource(name = "zookeeperRegisterFactory")
    private RegisterFactory registerFactory;

    @Autowired
    private EnvironmentRepository environmentRepository;

    /**
     * 根据环境创建注册器
     *
     * @param environmentPO
     * @return
     */
    public Register createRegister(EnvironmentPO environmentPO) {
        if (environmentPO != null) {
            try {
                Register register = registerFactory.createRegister(environmentPO);
                return setRegister(environmentPO.getId(), register);
            } catch (Exception e) {
                LOGGER.error("Create config register service[env={},ips={}] failed.exception:{}",
                        environmentPO.getLabel(), environmentPO.getIps(), e.getMessage());
            }
        }
        return null;
    }

    public Register getRegister(long envId) {
        return REGISTERS.get(envId);
    }

    /**
     * 获取所有的注册环境信息
     *
     * @return 环境信息列表
     */
    public Set<Long> getRegisterEnvIds() {
        return REGISTERS.keySet();
    }

    public void removeRegister(long envId) {
        Register register = REGISTERS.remove(envId);
        if (register != null) {
            destoryRegister(register);
        }
    }

    private Register setRegister(long envId, Register register) {
        Register oldRegister = REGISTERS.putIfAbsent(envId, register);
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
