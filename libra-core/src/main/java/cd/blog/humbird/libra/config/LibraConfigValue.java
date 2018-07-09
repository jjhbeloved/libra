package cd.blog.humbird.libra.config;

import cd.blog.humbird.libra.X;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author david
 * @since created by on 18/7/5 00:35
 */
@Configuration
public class LibraConfigValue {

    @Autowired
    private X x;

//    @Bean(initMethod = "init")
    @Bean()
    public String init() {
        System.out.println("hh");
        X c = x.getBean("x_warm", X.class);
        c.x();
        return "";
    }

    @Bean("xwam")
    public String x() {
        return "ox";
    }
}
