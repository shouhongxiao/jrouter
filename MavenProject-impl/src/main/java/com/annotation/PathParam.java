package com.annotation;

import java.lang.annotation.*;

/**
 * Created by shouh on 2016/10/11.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PathParam {

    /**
     * Defines the name of the URI template parameter whose value will be used
     * to initialize the value of the annotated method parameter, class field or
     * property. See {@link Path#value()} for a description of the syntax of
     * template parameters.
     *
     * <p>E.g. a class annotated with: {@code @Path("widgets/{id}")}
     * can have methods annotated whose arguments are annotated
     * with {@code @PathParam("id")}.
     */
    String value();
}