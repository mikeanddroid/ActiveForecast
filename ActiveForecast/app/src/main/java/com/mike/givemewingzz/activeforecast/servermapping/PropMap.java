package com.mike.givemewingzz.activeforecast.servermapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PropMap{
    String  serverFieldName() default "";
    boolean  localOnly() default false;
}
