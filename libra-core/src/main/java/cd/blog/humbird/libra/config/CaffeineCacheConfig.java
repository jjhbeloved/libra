package cd.blog.humbird.libra.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by david on 2018/7/12.
 */
@Configuration
public class CaffeineCacheConfig {

    public static Caffeine<Object, Object> buildCache(String name, long timeOut, long size) {
        return Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(timeOut, TimeUnit.SECONDS)
                .maximumSize(size);
    }

    @Bean(value = "caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        return new CaffeineCacheManager();
    }

}
