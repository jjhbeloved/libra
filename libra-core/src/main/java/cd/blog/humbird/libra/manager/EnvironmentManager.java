package cd.blog.humbird.libra.manager;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.register.Register;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import cd.blog.humbird.libra.repository.RegisterRepository;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by david on 2018/7/12.
 */
@Component
public class EnvironmentManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentManager.class);

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private RegisterRepository registerRepository;

    /**
     * 必须保证所有环境都能连接
     */
    @PostConstruct
    public void init() {
        List<Environment> environments = environmentRepository.findAll();
//        new Thread(() -> {
            if (!CollectionUtils.isEmpty(environments)) {
                for (Environment environment : environments) {
                    Register register = registerRepository.createRegister(environment);
                    if (register == null) {
                        LOGGER.error("Build config register service[env={}] failed while environment initialize.exception:{}",
                                environment.getLabel());
                    }
                }
            }
//        }).start();
    }


    /**
     * 查询数据并且刷新注册器
     */
    public void findAllAndRefresh() {
        List<Environment> environments = environmentRepository.findAll();
        refreshRegisters(environments);
    }

    /**
     * 刷新注册器
     * 1, 去除变更了地址的注册器
     * 2. 将无效的地址注册器销毁
     * 3. 将新增的地址注册器创建
     *
     * @param environments
     */
    private void refreshRegisters(List<Environment> environments) {
        Set<Long> allEnvIds = Sets.newHashSet();
        Map<Long, Environment> allEnvs = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(environments)) {
            for (Environment environment : environments) {
                long envId = environment.getId();
                allEnvIds.add(envId);
                allEnvs.put(envId, environment);
                Register register = registerRepository.getRegister(envId);
                if (register != null && !StringUtils.equals(register.getAddresses(), environment.getIps())) {
                    registerRepository.removeRegister(envId);
                }
            }
        }
        Set<Long> registeredEnvIds = registerRepository.getRegisterEnvIds();
        Collection<Long> needRemoveEnvIds = CollectionUtils.subtract(registeredEnvIds, allEnvIds);
        Collection<Long> needCreateEnvIds = CollectionUtils.subtract(allEnvIds, registeredEnvIds);
        for (Long envId : needRemoveEnvIds) {
            registerRepository.removeRegister(envId);
        }
        for (Long envId : needCreateEnvIds) {
            registerRepository.createRegister(allEnvs.get(envId));
        }
    }
}
