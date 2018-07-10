package cd.blog.humbird.libra.spring;

import cd.blog.humbird.libra.cli.config.ConfigLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * @author david
 * @since created by on 18/7/9 23:28
 */
public class LibraPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, EnvironmentAware {

    public String propertiesPath;
    public boolean includeLocalProps;

    private String beanName;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private Binder localProperties;

    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
        if (propertiesPath != null) {
            System.setProperty(ConfigLoader.KEY_PROPERTIES_FILE, propertiesPath);
        }
    }

    public void setIncludeLocalProps(boolean includeLocalProps) {
        this.includeLocalProps = includeLocalProps;
        System.setProperty(ConfigLoader.KEY_INCLUDE_LOCAL_PROPS, String.valueOf(includeLocalProps));
    }

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
        this.localProperties = Binder.get(environment);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties properties)
            throws BeansException {
        System.out.println("::::::::::::::::::::::::::::::::");
        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            // Check that we're not parsing our own bean definition,
            // to avoid failing on unresolvable placeholders in properties file locations.
            if (!(beanName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
                BeanDefinition beanDefinition = beanFactoryToProcess.getBeanDefinition(beanName);
                processPlaceholderProperties(beanDefinition);
//                processAnnotatedProperties(beanFactoryToProcess, beanNames[i]);
            }
        }
        super.processProperties(beanFactoryToProcess, properties);
    }

    private void processPlaceholderProperties(BeanDefinition beanDefinition) {
        if (beanDefinition.hasPropertyValues()) {
            MutablePropertyValues pvs = beanDefinition.getPropertyValues();
            PropertyValue[] pvArray = pvs.getPropertyValues();
            for (PropertyValue pv : pvArray) {
                Object v = pv.getValue();
                System.out.print(v + " ------- ");
                if (v instanceof TypedStringValue) {
                    String value = ((TypedStringValue) v).getValue();
                    System.out.println(value);

                }
            }
        }
    }

    /**
     * 重写对 properties的解析方法
     * 如果本地存在对应的properties, 读取本地properties
     * 否则读取服务端配置的properties
     *
     * @param placeholder 关键字
     * @param props       配置信息
     * @return 值(兜底为null)
     */
    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        BindResult<String> br = localProperties.bind(placeholder, String.class);
        return br.orElseGet(() -> "nu.ll");
    }

    public <T> T getBean(String name, Class<T> clazz) {
//       return beanFactory.getBean(name, clazz);
        return applicationContext.getBean(name, clazz);
    }

    public void x() {
        System.out.println(beanName);
        System.getProperties().forEach((o, o2) -> {
            System.out.println(o + ":" + o2);
        });
    }
}
