package cd.blog.humbird.libra.helper;

import cd.blog.humbird.libra.entity.Environment;

import java.util.function.Predicate;

/**
 * Created by david on 2018/7/13.
 */
public class EnvironmentHelper {

    // 判断环境是否可用
    public static final Predicate<Environment> IS_USED = env -> env.getStatus() == 0;

}
