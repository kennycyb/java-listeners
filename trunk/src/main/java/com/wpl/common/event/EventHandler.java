package com.wpl.common.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a method to handle a specified event.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface EventHandler {

    /**
     * The class type of the event that will be handled by this method.
     * 
     * @return class
     */
    public Class<?> value();
}
