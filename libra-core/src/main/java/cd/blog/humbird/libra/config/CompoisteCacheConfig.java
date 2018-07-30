package cd.blog.humbird.libra.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author david
 * @since created by on 2018/7/24 23:13
 */
@Component
@EnableCaching
public class CompoisteCacheConfig implements CachingConfigurer {

    @Resource(name = "caffeineCacheManager")
    private CacheManager caffeineCacheManager;

    @Bean(name = "localCacheManager")
    @Override
    public CacheManager cacheManager() {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        List<CacheManager> cacheManagers = new ArrayList<>();
        cacheManagers.add(caffeineCacheManager);
        compositeCacheManager.setCacheManagers(cacheManagers);
        return compositeCacheManager;
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object param : params) {
                if (param != null) {
                    sb.append(param.toString());
                }
            }
            return sb.toString();
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

}
