package cd.blog.humbird.libra;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author david
 * @since created by on 18/7/9 23:28
 */
@Component("x_warm")
public class X implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, EnvironmentAware {

    private String beanName;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private Binder binder;

//    @Value("${application.name}")
//    private String xn;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.binder = Binder.get(environment);
    }

    public <T> T getBean(String name, Class<T> clazz) {
//       return beanFactory.getBean(name, clazz);
        return applicationContext.getBean(name, clazz);
    }

    @Value("${info.address}")
    private String address;

    public void x() {
        System.out.println(address);
        System.out.println(binder.bind("application.name", String.class).get());
        System.out.println(beanName);
    }

}
