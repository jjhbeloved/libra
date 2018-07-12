package cd.blog.humbird.libra.register;

import cd.blog.humbird.libra.entity.Environment;

/**
 * @author david
 * @since created by on 18/7/12 03:41
 */
public interface RegisterFactory {

    Register createRegister(Environment environment) throws Exception;

}
