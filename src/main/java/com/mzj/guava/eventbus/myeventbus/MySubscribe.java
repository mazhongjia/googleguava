package com.mzj.guava.eventbus.myeventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)///这个注解是runtime的
@Target(ElementType.METHOD)//只能标记在method上
public @interface MySubscribe
{
    String topic() default "default-topic";
}
