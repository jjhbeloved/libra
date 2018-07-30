package cd.blog.humbird.libra.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static cd.blog.humbird.libra.model.em.CacheEnum.ConfigLocalCache;
import static cd.blog.humbird.libra.model.em.CacheEnum.EnvLocalCache;
import static cd.blog.humbird.libra.model.em.CacheEnum.SystemLocalCache;
import static cd.blog.humbird.libra.model.em.CacheEnum.TeamLocalCache;
import static cd.blog.humbird.libra.model.em.CacheEnum.UserLocalCache;

/**
 * @author david
 * @since created by on 2018/7/12 23:13
 */
@Configuration
public class CaffeineCacheConfig {

    private CaffeineCache buildCache(String key, long timeOut, long size) {
        return new CaffeineCache(key,
                Caffeine.newBuilder()
                        .recordStats()
                        .expireAfterWrite(timeOut, TimeUnit.SECONDS)
                        .maximumSize(size)
                        .build()
        );
    }

    @Bean(value = "caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        Cache user = buildCache(UserLocalCache.getCode(), UserLocalCache.getTimeOut(), UserLocalCache.getMaxSize());
        Cache system = buildCache(SystemLocalCache.getCode(), SystemLocalCache.getTimeOut(), SystemLocalCache.getMaxSize());
        Cache env = buildCache(EnvLocalCache.getCode(), EnvLocalCache.getTimeOut(), EnvLocalCache.getMaxSize());
        Cache team = buildCache(TeamLocalCache.getCode(), TeamLocalCache.getTimeOut(), TeamLocalCache.getMaxSize());
        Cache config = buildCache(ConfigLocalCache.getCode(), ConfigLocalCache.getTimeOut(), ConfigLocalCache.getMaxSize());
        manager.setCaches(
                Lists.newArrayList(user, system, env, team, config)
        );
        return manager;
    }

}
