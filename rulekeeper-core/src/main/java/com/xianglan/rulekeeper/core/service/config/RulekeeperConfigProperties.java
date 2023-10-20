package com.xianglan.rulekeeper.core.service.config;

import com.xianglan.rulekeeper.core.constant.RulekeeperConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = RulekeeperConstant.PROPERTIES_PREFIX)
public class RulekeeperConfigProperties {
    /**
     * 是否使用rulekeeper规则引擎
     */
    private Boolean enabled;

    /**
     * 主配置名
     */
    private String configName;
}
