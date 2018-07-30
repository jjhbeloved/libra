package cd.blog.humbird.libra.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author david
 * @since created by on 2018/7/10 23:12
 */
public class LibraNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("config", new LibraConfigDefinitionParser());
    }
}
