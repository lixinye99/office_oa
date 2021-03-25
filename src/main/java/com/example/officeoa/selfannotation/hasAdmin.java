package com.example.officeoa.selfannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: LiXinYe
 * @Description: 自定义注解 ： 加在在需要系统管理员权限的方法上
 * @Date: Create in 17:21 2021/3/13
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface hasAdmin {
    boolean required() default true;
}
