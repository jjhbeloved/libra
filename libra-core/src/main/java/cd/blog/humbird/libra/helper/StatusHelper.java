package cd.blog.humbird.libra.helper;

import cd.blog.humbird.libra.entity.Environment;
import cd.blog.humbird.libra.entity.User;

import java.util.function.Predicate;

/**
 * Created by david on 2018/7/13.
 */
public class StatusHelper {

    // 判断环境是否可用
    public static final Predicate<Environment> IS_ENV_USED = env -> env.getStatus() == 0;

    // 判断环境是否可用
    public static final Predicate<User> IS_USER_USED = user -> user.getStatus() == 0;

}
