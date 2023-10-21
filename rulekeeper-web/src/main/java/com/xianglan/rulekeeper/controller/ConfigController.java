package com.xianglan.rulekeeper.controller;

import com.alibaba.fastjson2.JSON;
import com.xianglan.rulekeeper.annotation.RulekeeperAspect;
import com.xianglan.rulekeeper.annotation.RulekeeperResult;
import com.xianglan.rulekeeper.core.domain.MainConfig;
import com.xianglan.rulekeeper.core.service.boostrap.RulekeeperBaseConfig;
import com.xianglan.rulekeeper.core.service.config.RulekeeperConfigProperties;
import com.xianglan.rulekeeper.domain.RulekeeperParam;
import com.xianglan.rulekeeper.enums.RespStatusEnum;
import com.xianglan.rulekeeper.service.RulekeeperConfigService;
import com.xianglan.rulekeeper.vo.RulekeeperConfigListVo;
import com.xianglan.rulekeeper.vo.basic.BasicResultVO;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 配置接口
 */
@RestController
@Slf4j
@RulekeeperResult
@RulekeeperAspect
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private RulekeeperBaseConfig baseConfigService;

    @Autowired
    private RulekeeperConfigService configService;

    @Autowired
    protected RulekeeperConfigProperties configProperties;

    @RequestMapping("/add")
    public BasicResultVO addConfig(@RequestBody RulekeeperParam rulekeeperParam){
        MainConfig mainConfig = JSON.parseObject(baseConfigService.getConfigValueByName(configProperties.getConfigName()), MainConfig.class);
        if (mainConfig.getInstanceNames().contains(rulekeeperParam.getName())) {
            return BasicResultVO.fail(RespStatusEnum.FAIL, "已存在相同名的脚本，禁止添加");
        }
        configService.addConfig(rulekeeperParam);
        return BasicResultVO.success();
    }
    @RequestMapping("/get")
    public List<RulekeeperConfigListVo> getAllConfig(String keywords){
        return configService.getAllConfig(keywords);
    }
    @RequestMapping("/delete")
    public BasicResultVO deleteConfig(String name){
        configService.deleteConfig(name);
        return BasicResultVO.success();
    }
}
