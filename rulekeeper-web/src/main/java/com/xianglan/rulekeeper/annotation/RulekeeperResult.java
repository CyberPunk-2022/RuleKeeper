package com.xianglan.rulekeeper.annotation;


import java.lang.annotation.*;

/**
 * 用注解代实现统一返回格式
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RulekeeperResult {
}
