package com.annotation;

import java.lang.annotation.*;

/**
 * Created by shouh on 2016/10/11.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RegisterAddrAnnotation {
    String name() default "";
    String value() default "";
    DeliverType deliver() default DeliverType.GET;
}
