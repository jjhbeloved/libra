package cd.blog.humbird.libra.service;

import org.springframework.stereotype.Service;

/**
 * Created by david on 2018/7/12.
 */
@Service
public class RegisterService {

//    @Autowired
//    private RegisterRepository registerRepository;
//
//    @Autowired
//    private EnvironmentRepository environmentRepository;

//    /**
//     * 根据环境Id创建注册器
//     * 如果不存在, 创建新的注册器
//     *
//     * @param envId
//     * @return
//     * @throws Exception
//     */
//    public Register createRegister(long envId) {
//        Register register = registerRepository.getRegister(envId);
//        if (register != null) {
//            return register;
//        } else {
//            Environment environment = environmentRepository.findById(envId);
//            if (!StatusHelper.IS_USED.test(environment)) {
//                return null;
//            }
//            return registerRepository.createRegister(environment);
//        }
//    }
}
