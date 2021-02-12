package cd.blog.humbird.libra.helper;

import cd.blog.humbird.libra.model.po.EnvironmentPO;
import cd.blog.humbird.libra.model.po.UserPO;

import java.util.function.Predicate;

/**
 * Created by david on 2018/7/13.
 */
public class StatusHelper {

    /**
     * 判断环境是否可用
     */
    public static final Predicate<EnvironmentPO> IS_ENV_USED = env -> env.getStatus() == 0;

    /**
     * 判断用户是否激活
     */
    public static final Predicate<UserPO> IS_USER_USED = user -> user.getStatus() == 0;

}
