package cd.blog.humbird.libra.cache;

import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by david on 2018/7/12.
 */
@Configuration
public class ClusterCache extends BaseCache {

    private static final String CLUSTER = "clusterCache";
    private static final String CLUSTER_SHORT = "clusterShortCache";

    @Bean
    public Cache caffeineClusterCache() {
        return createCaffeineCache(CLUSTER);
    }

    @Bean
    public Cache caffeineClusterShortCache() {
        return createCaffeineCache(CLUSTER_SHORT);
    }
}
