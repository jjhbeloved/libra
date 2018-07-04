package cd.blog.humbird.libra.spring;

/**
 * @author david
 * @since created by on 18/7/5 00:50
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LibraConfig {

    /**
     * Pattern of value:
     * {"key"} only key, default value is null
     * {"key:defaultValue"} key & default value
     */
    String value() default "";

    /**
     * the key part of value
     */
    String key() default "";

    /**
     * the default value part of value
     */
    String defaultValue() default "";

}
