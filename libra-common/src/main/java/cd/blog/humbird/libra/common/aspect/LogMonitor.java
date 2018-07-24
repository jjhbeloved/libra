package cd.blog.humbird.libra.common.aspect;

import java.lang.annotation.*;

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
