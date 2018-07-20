package cd.blog.humbird.libra.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by david on 2018/7/20.
 * 方法响应时间切面
 * SpringBoot 需要私用 cglib 才能支持 class 级别的切面
 * 使用 @EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true) 启动cglib aop
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMonitor {
}
