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
    private static final String CLUSTER_USER = "clusterUserCache";
    private static final String CLUSTER_SHORT = "clusterShortCache";

    @Bean
    public Cache localClusterCache() {
        return createCaffeineCache(CLUSTER, 1000, 1800);
    }

    @Bean
    public Cache localClusterUserCache() {
        return createCaffeineCache(CLUSTER_USER, 20000, 1800);
    }

    @Bean
    public Cache localClusterShortCache() {
        return createCaffeineCache(CLUSTER_SHORT, 10000, 600);
    }
}
