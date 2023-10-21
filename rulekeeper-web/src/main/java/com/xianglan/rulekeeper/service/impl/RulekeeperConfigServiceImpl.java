package com.xianglan.rulekeeper.service.impl;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Throwables;
import com.xianglan.rulekeeper.core.domain.MainConfig;
import com.xianglan.rulekeeper.core.service.boostrap.RulekeeperBaseConfig;
import com.xianglan.rulekeeper.core.service.config.RulekeeperConfigProperties;
import com.xianglan.rulekeeper.domain.RulekeeperParam;
import com.xianglan.rulekeeper.enums.RuleType;
import com.xianglan.rulekeeper.parse.ParseConfig;
import com.xianglan.rulekeeper.service.RulekeeperConfigService;
import com.xianglan.rulekeeper.vo.RulekeeperConfigListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置实现类
 */
@Service
@Slf4j
public class RulekeeperConfigServiceImpl implements RulekeeperConfigService {

    @Autowired
    private RulekeeperBaseConfig baseConfigService;

    @Autowired
    private RulekeeperConfigProperties configProperties;
    
    @Autowired
    private ParseConfig parseConfig;



    /**
     * 添加配置
     *
     * @param rulekeeperParam
     */
    @Override
    public void addConfig(RulekeeperParam rulekeeperParam) {
        try {
            // 主配置
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            mainConfig.getInstanceNames().add(rulekeeperParam.getName());
            mainConfig.setUpdateTime(System.currentTimeMillis());
            baseConfigService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));

            // 脚本配置
            String resultCode;
            if (RuleType.CODE.getCode().equals(rulekeeperParam.getRuleType())) {
                resultCode = rulekeeperParam.getRuleLogicCode();
            } else {
                String template = new FileReader("generateGroovyCodeTemplate").readString();
                resultCode = template.replace("_PACKAGE_NAME_", rulekeeperParam.getName()
                                .substring(0, rulekeeperParam.getName().lastIndexOf(".")))
                        .replace("_SCRIPT_NAME_", rulekeeperParam.getName())
                        .replace("_CLASS_NAME_", rulekeeperParam.getName().substring(rulekeeperParam.getName().lastIndexOf(".") + 1))
                        .replace("_CONDITION_", parseConfig.parse(JSON.toJSONString(rulekeeperParam.getRuleLogicGraph())));
                baseConfigService.addOrUpdateProperty(rulekeeperParam.getName(), resultCode);
            }
            baseConfigService.addOrUpdateProperty(rulekeeperParam.getName(), resultCode);

        } catch (Exception e) {
            log.error("RulekeeperConfigService#addConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public List<RulekeeperConfigListVo> getAllConfig(String keywords) {
        List<RulekeeperConfigListVo> result = new ArrayList<>();
        try {
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            for (String instanceName : mainConfig.getInstanceNames()) {
                if (StringUtils.hasText(keywords) && !instanceName.contains(keywords)) {
                    continue;
                }
                RulekeeperConfigListVo rulekeeperConfigListVo = RulekeeperConfigListVo.builder()
                        .name(instanceName).ruleLogicCode(baseConfigService
                                .getConfigValueByName(instanceName)).build();
                result.add(rulekeeperConfigListVo);
            }
        } catch (Exception e) {
            log.error("RulekeeperConfigService#getAllConfig fail:{}", Throwables.getStackTraceAsString(e));
        }

        return result;
    }

    @Override
    public void deleteConfig(String name) {
        try {
            MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
            mainConfig.getInstanceNames().remove(name);
            baseConfigService.addOrUpdateProperty(configProperties.getConfigName(), JSON.toJSONString(mainConfig));
            baseConfigService.removeProperty(name);
        } catch (Exception e) {
            log.error("HadesConfigService#deleteConfig fail:{}", Throwables.getStackTraceAsString(e));
        }
    }
}
