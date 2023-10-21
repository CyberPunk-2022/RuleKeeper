package com.xianglan.rulekeeper.annotation;


import java.lang.annotation.*;

/**
 * 接口切面注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RulekeeperAspect {
}
