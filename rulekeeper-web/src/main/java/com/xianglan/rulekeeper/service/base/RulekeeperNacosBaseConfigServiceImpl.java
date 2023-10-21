package com.xianglan.rulekeeper.service.base;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.shaded.com.google.common.base.Throwables;
import com.xianglan.rulekeeper.core.constant.RulekeeperConstant;
import com.xianglan.rulekeeper.core.service.boostrap.BaseRulekeeperBaseConfig;
import com.xianglan.rulekeeper.core.service.config.RulekeeperConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * Nacos配置实现类
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "nacos.config.enabled", havingValue = "true")
public class RulekeeperNacosBaseConfigServiceImpl extends BaseRulekeeperBaseConfig implements Listener {
    @Autowired
    protected RulekeeperConfigProperties configProperties;

    @NacosInjected
    private ConfigService configService;

    @Override
    public void addOrUpdateProperty(String key, String value) {
        try {
            configService.publishConfig(key, RulekeeperConstant.NACOS_DEFAULT_GROUP, value);
        } catch (Exception e) {
            log.error("RulekeeperConfigService#addOrUpdateProperty fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public void removeProperty(String key) {
        try {
            configService.removeConfig(key, RulekeeperConstant.NACOS_DEFAULT_GROUP);
        } catch (NacosException e) {
            log.error("RulekeeperConfigService#removeProperty fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getConfigValueByName(String configName) {
        try {
            return configService.getConfig(configName, RulekeeperConstant.NACOS_DEFAULT_GROUP, 3000L);
        } catch (NacosException e) {
            log.error("RulekeeperConfigService#getConfigValueByName key:[{}],fail:{}", configName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public void receiveConfigInfo(String mainConfig) {
    }

    @Override
    public void addListener() {
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
