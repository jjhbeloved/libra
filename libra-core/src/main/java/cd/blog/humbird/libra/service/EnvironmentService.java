package cd.blog.humbird.libra.service;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.helper.StatusHelper;
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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by david on 2018/7/12.
 */
@Service
public class EnvironmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentService.class);

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
//        new Thread(() -> { // 使用线程可能会造成 线程未执行完之前,注册缓存数据不全
        if (!CollectionUtils.isEmpty(environments)) {
            for (Environment environment : environments) {
                if (StatusHelper.IS_ENV_USED.test(environment)) {
                    Register register = registerRepository.createRegister(environment);
                    if (register == null) {
                        LOGGER.error("Build config register service[envLabel={}] failed while environment initialize",
                                environment.getLabel());
                    }
                } else {
                    LOGGER.info("Environment [envId:{},envLabel={}] is closed.",
                            environment.getId(), environment.getLabel());
                }
            }
        }
//        }).start();
    }


    /**
     * 查询数据并且刷新注册器
     */
    public List<Environment> findAll() {
        return environmentRepository.findAll();
    }

    /**
     * 1. 查询所有环境信息
     * 2. 只注册在线的环境信息
     *
     * @return 所有环境信息
     */
    public List<Environment> findAllAndrefresh() {
        List<Environment> environments = findAll();
        refreshRegisters(environments);
        return environments;
    }

    public Environment findById(long id) {
        return environmentRepository.findById(id);
    }

    public Environment findByName(String name) {
        return environmentRepository.findByName(name);
    }

    public long create(String name, String label, String ips, int status) {
        Environment environment = new Environment();
        environment.setName(name);
        environment.setLabel(label);
        environment.setIps(ips);
        environment.setStatus(status);
        return environmentRepository.create(environment);
    }

    public void update(long id, String ips, int status) {
        Environment environment = new Environment();
        environment.setId(id);
        environment.setIps(ips);
        environment.setStatus(status);
        environmentRepository.update(environment);
    }

    public void delete(long id) {
        environmentRepository.delete(id);
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
        needCreateEnvIds.stream()
                .map(allEnvs::get)
                .filter(Objects::nonNull)
                .filter(StatusHelper.IS_ENV_USED)
                .forEach(environment -> registerRepository.createRegister(environment));
    }
}
