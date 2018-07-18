package cd.blog.humbird.libra.cache;

import cd.blog.humbird.libra.config.CaffeineCacheConfig;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import javax.annotation.Resource;

/**
 * Created by david on 2018/7/12.
 */
public abstract class BaseCache {

    static final String PREFIX_CAFFEINE = "caffeine_";
    static final String PREFIX_REDIS = "redis_";

    static final long TIMEOUT = 300;
    static final long MAX_SIZE = 100;

    @Resource(name = "caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    public Cache createCaffeineCache(String name, long maxSize, long timeout) {
        CaffeineCacheManager manager = ((CaffeineCacheManager) caffeineCacheManager);
        String key = PREFIX_CAFFEINE + name;
        Caffeine<Object, Object> caffeine = CaffeineCacheConfig.buildCache(key, timeout, maxSize);
        manager.setCaffeine(caffeine);
        return manager.getCache(key);
    }
}
