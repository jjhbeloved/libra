package cd.blog.humbird.libra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author david
 * @since created by on 18/7/5 00:35
 */
@Configuration
public class LibraConfigValue {

    //    @Bean(initMethod = "init")
    @Bean()
    public String init() {
        return "";
    }

    @Bean("xwam")
    public String x() {
        return "ox";
    }
}
