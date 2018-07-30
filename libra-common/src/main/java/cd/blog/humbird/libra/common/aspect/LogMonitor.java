package cd.blog.humbird.libra.common.aspect;

import java.lang.annotation.*;

/**
 * @author david
 * @since created by on 18/7/30 23:09
 * 方法响应时间切面
 * SpringBoot 需要私用 cglib 才能支持
 * <p>
 * class 级别的切面
 * 使用 @EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true) 启动cglib aop
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMonitor {
}
