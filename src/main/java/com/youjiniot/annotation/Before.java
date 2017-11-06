package com.youjiniot.annotation;

import java.lang.annotation.*;

/**
 * Created by youjin on 2017/11/6.
 */
/**
 *自定义注解 拦截Controller
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Before {
    Class<?>[] value();
}
