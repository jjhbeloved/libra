package cd.blog.humbird.libra.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * Created by david on 2018/7/10.
 */
public class LibraConfigDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String propertiesPath = element.getAttribute("propertiesPath");
        String includeLocalProps = element.getAttribute("includeLocalProps");
        String order = element.getAttribute("order");
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(LibraPlaceholderConfigurer.class);

        if (StringUtils.hasText(propertiesPath)) {
            builder.addPropertyValue("propertiesPath", propertiesPath);
        }
        if (StringUtils.hasText(includeLocalProps)) {
            builder.addPropertyValue("includeLocalProps", Boolean.valueOf(includeLocalProps));
        }
        if (StringUtils.hasText(order)) {
            builder.addPropertyValue("order", Integer.valueOf(order));
        }
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        parserContext.getRegistry().registerBeanDefinition("libra-config", beanDefinition);

        return null;
    }

}
