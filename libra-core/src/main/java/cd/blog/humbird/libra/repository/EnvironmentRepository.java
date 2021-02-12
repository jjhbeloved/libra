package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.model.po.EnvironmentPO;
import cd.blog.humbird.libra.model.po.OpLogPO;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.model.em.OpLogTypeEnum;
import cd.blog.humbird.libra.model.domain.UserDO;
import cd.blog.humbird.libra.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static cd.blog.humbird.libra.helper.StatusHelper.IS_ENV_USED;
import static cd.blog.humbird.libra.model.em.CacheEnum.EnvLocalCache;

/**
 * 每个环境都对应一个注册管理器
 * <p>
 * Created by david on 2018/7/11.
 */
@Repository
public class EnvironmentRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);
	private static final String LISTS = "lists";
	private static final String ID_ = "id-";
	private static final String NAME_ = "name-";

	@Autowired
	private EnvironmentMapper environmentMapper;

	@Autowired
	private OpLogRepository opLogRepository;

	@Resource(name = "localCacheManager")
	private CacheManager cacheManager;

	@Cacheable(value = "envLocalCache", key = LISTS, unless = "#result == null || #result.size() == 0")
	public List<EnvironmentPO> findAll() {
		return environmentMapper.findAll();
	}

	@Cacheable(value = "envLocalCache", key = "'id-' + #id", unless = "#result == null")
	public EnvironmentPO findById(long id) {
		return environmentMapper.findById(id);
	}

	@Cacheable(value = "envLocalCache", key = "'name-' + #name", unless = "#result == null")
	public EnvironmentPO findByName(String name) {
		return environmentMapper.findByName(name);
	}

	public long create(EnvironmentPO environmentPO) {
		try {
			UserDO userDO = UserUtils.getUser();
			environmentPO.setCreator(userDO.getName());
			environmentPO.setModifier(userDO.getName());
			environmentMapper.insert(environmentPO);
			long id = environmentPO.getId();
			String content = String.format("创建%s环境,[IP:%s,是否上线:%s]",
					environmentPO.getLabel(), environmentPO.getIps(), IS_ENV_USED.test(environmentPO)
			);
			opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Env_Add.getValue(), userDO.getId(), userDO.getFrIp(), id, null, content));
			return id;
		} finally {
			Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
			if (cache != null) {
				cache.evict(LISTS);
			}
		}
	}

	/**
	 * 只允许修改 IPS 和 状态
	 *
	 * @param prvEnv      原环境信息
	 * @param environmentPO 新环境信息
	 */
	public void update(EnvironmentPO prvEnv, EnvironmentPO environmentPO) {
		if (prvEnv == null) {
			return;
		}
		long id = environmentPO.getId();
		try {
			UserDO u = UserUtils.getUser();
			environmentPO.setModifier(u.getName());
			environmentMapper.update(environmentPO);
			String content = String.format("编辑%s环境,[IP:%s,是否上线:%s]->[IP:%s,是否上线:%s]",
					prvEnv.getLabel(), prvEnv.getIps(), IS_ENV_USED.test(prvEnv), environmentPO.getIps(), IS_ENV_USED.test(environmentPO)
			);
			opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Env_Edit.getValue(), u.getId(), u.getFrIp(), id, null, content));
		} finally {
			Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
			if (cache != null) {
				cache.evict(LISTS);
				cache.evict(ID_ + id);
				cache.evict(NAME_ + environmentPO.getName());
			}
		}
	}

	/**
	 * @param environmentPO 要删除的环境信息
	 */
	public void delete(EnvironmentPO environmentPO) {
		if (environmentPO == null) {
			return;
		}
		long id = environmentPO.getId();
		try {
			environmentMapper.delete(id);
			UserDO userDO = UserUtils.getUser();
			String content = "删除" + environmentPO.getLabel() + "环境";
			opLogRepository.insert(new OpLogPO(OpLogTypeEnum.Env_Delete.getValue(), userDO.getId(), userDO.getFrIp(), id, null, content));

		} finally {
			Cache cache = cacheManager.getCache(EnvLocalCache.getCode());
			if (cache != null) {
				cache.evict(LISTS);
				cache.evict(ID_ + id);
				cache.evict(NAME_ + environmentPO.getName());
			}
		}
	}

}
