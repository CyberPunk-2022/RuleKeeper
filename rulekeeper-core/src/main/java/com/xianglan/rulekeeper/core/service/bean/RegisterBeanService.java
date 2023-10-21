package com.xianglan.rulekeeper.core.service.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 将groovy class 注册到 Spring IOC 容器中
 */
@Service
@Slf4j
public class RegisterBeanService {

    @Autowired
    private ApplicationContext applicationContext;

    public <T> T registerBean(String name, Class<T> clazz, Object... args) {
        /**
         * ConfigurableApplicationContext是Spring框架实现的一种ApplicationContext接口的子类，
         * 它提供了可配置的应用程序上下文环境。增加了许多可配置的选项，比如可以动态地加载和卸载bean定义，
         * 可以在应用程序运行时添加/移除监听器，还可以手动刷新上下文等
         */
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        /**
         * 向BeanDefinitionBuilder添加构造函数参数
         */
        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        if (context.containsBean(name)){
            log.info("bean:[{}]在系统中已存在,接下来对其进行替换操作", name);
            beanFactory.removeBeanDefinition(name);
        }
        beanFactory.registerBeanDefinition(name,beanDefinition);
        return applicationContext.getBean(name, clazz);
    }
}
