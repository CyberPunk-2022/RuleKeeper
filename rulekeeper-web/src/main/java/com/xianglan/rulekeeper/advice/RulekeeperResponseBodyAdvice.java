package com.xianglan.rulekeeper.advice;


import com.xianglan.rulekeeper.annotation.RulekeeperResult;
import com.xianglan.rulekeeper.vo.basic.BasicResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 统一返回结构
 */
@ControllerAdvice(basePackages = "com.xianglan.rulekeeper.controller")
public class RulekeeperResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final String RETURN_CLASS = "BasicResultVO";
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // 检测有该注解的方法或类
        return methodParameter.getContainingClass().isAnnotationPresent(RulekeeperResult.class) || methodParameter.hasMethodAnnotation(RulekeeperResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (Objects.nonNull(data) && Objects.nonNull(data.getClass())) {
            String simpleName = data.getClass().getSimpleName();
            if (RETURN_CLASS.equalsIgnoreCase(simpleName)) {
                return data;
            }
        }
        return BasicResultVO.success(data);
    }
}
