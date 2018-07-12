package cd.blog.humbird.libra.repository;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.mapper.EnvironmentMapper;
import cd.blog.humbird.libra.register.Register;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 每个环境都对应一个注册管理器
 * <p>
 * Created by david on 2018/7/11.
 */
@Lazy
@Repository
public class EnvironmentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentRepository.class);

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Resource(name = "caffeineClusterCache")
    private Cache cache;

    public List<Environment> findAll() {
        List<Environment> environments = cache.get("cache_env_list", List.class);
        if (CollectionUtils.isEmpty(environments)) {
            synchronized (this) {
                environments = cache.get("cache_env_list", List.class);
                if (CollectionUtils.isEmpty(environments)) {
                    environments = environmentMapper.findAll();
                    if (!CollectionUtils.isEmpty(environments)) {
                        cache.put("cache_env_list", environments);
                    }
                }
            }
        }
        return environments;
    }

    public Environment findById(long id) {
        List<Environment> environments = findAll();
        if (CollectionUtils.isEmpty(environments)) {
            return null;
        }
        for (Environment environment : environments) {
            if (id == environment.getId()) {
                return environment;
            }
        }
        return null;
    }

}
