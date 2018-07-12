package cd.blog.humbird.libra.manager;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.register.Register;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import cd.blog.humbird.libra.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by david on 2018/7/12.
 */
@Component
public class RegisterManager {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    /**
     * 根据环境Id创建注册器
     * 如果不存在, 创建新的注册器
     *
     * @param envId
     * @return
     * @throws Exception
     */
    public Register createRegister(long envId) {
        Register register = registerRepository.getRegister(envId);
        if (register != null) {
            return register;
        } else {
            Environment environment = environmentRepository.findById(envId);
            return registerRepository.createRegister(environment);
        }
    }
}
