package cd.blog.humbird.libra.service.impl;

import cd.blog.humbird.libra.model.po.EnvironmentPO;
import cd.blog.humbird.libra.helper.StatusHelper;
import cd.blog.humbird.libra.register.Register;
import cd.blog.humbird.libra.repository.EnvironmentRepository;
import cd.blog.humbird.libra.repository.RegisterRepository;
import cd.blog.humbird.libra.service.EnvironmentService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author david
 * @since created by on 2018/7/12 23:32
 */
@Service
public class EnvironmentServiceImpl implements EnvironmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentServiceImpl.class);

	@Autowired
	private EnvironmentRepository environmentRepository;

	@Autowired
	private RegisterRepository registerRepository;

	/**
	 * 必须保证所有环境都能连接
	 */
	@PostConstruct
	public void init() {
		List<EnvironmentPO> environmentPOS = environmentRepository.findAll();
//        new Thread(() -> { // 使用线程可能会造成 线程未执行完之前,注册缓存数据不全
		if (!CollectionUtils.isEmpty(environmentPOS)) {
			for (EnvironmentPO environmentPO : environmentPOS) {
				if (StatusHelper.IS_ENV_USED.test(environmentPO)) {
					Register register = registerRepository.createRegister(environmentPO);
					if (register == null) {
						LOGGER.error("Build config register service[envLabel={}] failed while environment initialize",
								environmentPO.getLabel());
					}
				} else {
					LOGGER.info("Environment [envId:{},envLabel={}] is closed.",
							environmentPO.getId(), environmentPO.getLabel());
				}
			}
		}
//        }).start();
	}


	/**
	 * 查询数据并且刷新注册器
	 */
	@Override
	public List<EnvironmentPO> listAll() {
		return environmentRepository.findAll();
	}

	/**
	 * 1. 查询所有环境信息
	 * 2. 只注册在线的环境信息
	 *
	 * @return 所有环境信息
	 */
	@Override
	public List<EnvironmentPO> listAllAndRefresh() {
		List<EnvironmentPO> environmentPOS = listAll();
		refreshRegisters(environmentPOS);
		return environmentPOS;
	}

	@Override
	public EnvironmentPO getById(Long id) {
		if(id == null) {
			return null;
		}
		return environmentRepository.findById(id);
	}

	@Override
	public EnvironmentPO getByName(String name) {
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return environmentRepository.findByName(name);
	}

	@Override
	public Long insert(String name, String label, String ips, Integer status) {
		EnvironmentPO environmentPO = new EnvironmentPO();
		environmentPO.setName(name);
		environmentPO.setLabel(label);
		environmentPO.setIps(ips);
		environmentPO.setStatus(Optional.ofNullable(status).orElse(0));
		return environmentRepository.create(environmentPO);
	}

	@Override
	public void update(Long id, String ips, Integer status) {
		if (id == null) {
			return;
		}
		EnvironmentPO existsEnv = environmentRepository.findById(id);
		EnvironmentPO environmentPO = new EnvironmentPO();
		environmentPO.setId(id);
		environmentPO.setIps(ips);
		environmentPO.setStatus(Optional.ofNullable(status).orElse(0));
		environmentRepository.update(existsEnv, environmentPO);
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			return;
		}
		EnvironmentPO environmentPO = environmentRepository.findById(id);
		environmentRepository.delete(environmentPO);
	}

	/**
	 * 刷新注册器
	 * 1, 去除变更了地址的注册器
	 * 2. 将无效的地址注册器销毁
	 * 3. 将新增的地址注册器创建
	 *
	 * @param environmentPOS 环境列表
	 */
	private void refreshRegisters(List<EnvironmentPO> environmentPOS) {
		Set<Long> allEnvIds = Sets.newHashSet();
		Map<Long, EnvironmentPO> allEnvs = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(environmentPOS)) {
			for (EnvironmentPO environmentPO : environmentPOS) {
				long envId = environmentPO.getId();
				allEnvIds.add(envId);
				allEnvs.put(envId, environmentPO);
				Register register = registerRepository.getRegister(envId);
				if (register != null && !StringUtils.equals(register.getAddresses(), environmentPO.getIps())) {
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
