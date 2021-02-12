package cd.blog.humbird.libra.cli;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author david
 * @since created by on 2018/7/11 23:12
 */
public class BaseUT {
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

}
