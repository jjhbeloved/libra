package cd.blog.humbird.libra.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by david on 2018/7/10.
 */
public class LibraNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("config", new LibraConfigDefinitionParser());
    }
}
