package cd.blog.humbird.libra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by david on 2018/7/10.
 */
@Component
public class X {

    @Value("${application.name}")
    private String name;

    @Value("${application.xx}")
    private String xx;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }
}
