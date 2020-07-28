package com.example.demo.APITest;


import javax.persistence.Inheritance;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited //Mybook.class 에서도 조회된다.
public @interface MyAnnotation {

    String name() default "hyunwoo";

    String value();

    int number() default 100;
}
