package top.mnsx.take_out.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/9/23 22:19
 * @Description: 不需要拦截
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Component
public @interface UnAuth {
}
