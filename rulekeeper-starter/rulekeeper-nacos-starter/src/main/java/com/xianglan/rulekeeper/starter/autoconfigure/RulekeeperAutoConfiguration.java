package com.xianglan.rulekeeper.starter.autoconfigure;


import com.xianglan.rulekeeper.core.constant.RulekeeperConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = com.alibaba.nacos.api.config.ConfigService.class)
@ConditionalOnProperty(value = RulekeeperConstant.RULEKEEPER_ENABLED_PROPERTIES, havingValue = "true")
@ComponentScan(RulekeeperConstant.SCAN_PATH)
public class RulekeeperAutoConfiguration {
}
