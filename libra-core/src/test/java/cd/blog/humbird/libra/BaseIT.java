package cd.blog.humbird.libra;


import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.repository.mapper.EnviornmentMapper;
import cd.blog.humbird.libra.spring.LibraPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author david
 * @since created by on 18/7/9 23:49
 */
@SpringBootTest(classes = Application.class)
public abstract class BaseIT extends AbstractTestNGSpringContextTests {

//    @Autowired
//    private X x;
//
//    public void go() {
//        System.out.println(x.getName());
//        System.out.println(x.getXx());
//    }
//
//    @Test
//    public void x() {
//        go();
//    }
}
