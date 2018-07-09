package cd.blog.humbird.libra;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * @author david
 * @since created by on 18/7/9 23:49
 */
@SpringBootTest(classes = Application.class)
public class BaseIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private X x;

    public void go() {
        X c = x.getBean("x_warm", X.class);
        c.x();
    }

    @Test
    public void x() {
        go();
    }
}
