package cd.blog.humbird.libra;

import com.alibaba.druid.filter.config.ConfigTools;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by david on 2018/7/11.
 */
public class BaseUT {
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void x() throws Exception {
        System.out.println(ConfigTools.decrypt("NqEOfteOBdxRuFqDqUFzzstQnCLdqZM0psxaNk+o1c3AKj3ncikWsCf0hOKrepq5bp5FNDdlvFb8SxubiEKi9Q=="));
    }
}
