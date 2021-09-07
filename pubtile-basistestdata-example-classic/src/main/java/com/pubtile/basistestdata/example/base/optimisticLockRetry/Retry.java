package com.pubtile.basistestdata.example.base.optimisticLockRetry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 *
 * @author jiayan
 * @version 0.0.1 2021-08-15
 * @since 0.0.1 2021-08-15
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retry {
    // 重试次数
    int times() default 3;
}