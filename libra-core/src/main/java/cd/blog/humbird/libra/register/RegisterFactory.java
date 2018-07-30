package cd.blog.humbird.libra.register;

import cd.blog.humbird.libra.model.po.EnvironmentPO;

/**
 * @author david
 * @since created by on 18/7/12 03:41
 */
public interface RegisterFactory {

    Register createRegister(EnvironmentPO environmentPO) throws Exception;

}
