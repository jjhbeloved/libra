package cd.blog.humbird.libra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author david
 * @since created by on 18/7/5 00:13
 */
@SpringBootApplication(scanBasePackages = "cd.blog.humbird.libra")
@ImportResource("classpath*:libra-config.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
