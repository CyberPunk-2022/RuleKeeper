package com.xianglan.rulekeeper.starter.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.base.Throwables;
import com.xianglan.rulekeeper.core.constant.RulekeeperConstant;
import com.xianglan.rulekeeper.core.service.boostrap.BaseRulekeeperBaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@Slf4j
public class NacosStarter extends BaseRulekeeperBaseConfig implements Listener {

    @NacosInjected
    private ConfigService configService;
    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String mainConfig) {
        log.info("分布式配置中心监听到[{}]数据更新:{}", configProperties.getConfigName(), mainConfig);
        bootstrap(mainConfig);
    }

    @Override
    public void addListener() {
        try{
            configService.addListener(configProperties.getConfigName(),RulekeeperConstant.NACOS_DEFAULT_GROUP,this);
            log.info("分布式配置中心配置[{}]监听器已启动", configProperties.getConfigName());
        } catch (Exception e) {
            log.error("HadesConfigService#refresh key:[{}] fail:{}", configProperties.getConfigName(), Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 通过文件名获取得到文件内容
     * @param configName
     * @return
     */
    @Override
    public String getConfigValueByName(String configName) {
        try{
            configService.getConfig(configName, RulekeeperConstant.NACOS_DEFAULT_GROUP,3000L);
        }catch (NacosException e){
            log.error("HadesConfigService#getConfigValueByName key:[{}],fail:{}", configName, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public void addOrUpdateProperty(String key, String value) {

    }
}
