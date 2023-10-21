package com.xianglan.rulekeeper.service;

import com.xianglan.rulekeeper.domain.RulekeeperParam;
import com.xianglan.rulekeeper.vo.RulekeeperConfigListVo;

import java.util.List;

/**
 * 配置服务
 */
public interface RulekeeperConfigService {

    /**
     * 添加配置
     *
     * @param rulekeeperParam
     */
    void addConfig(RulekeeperParam rulekeeperParam);

    /**
     * 获取所有的配置项(可输入关键词筛选)
     *
     * @param keywords 非必填
     * @return
     */
    List<RulekeeperConfigListVo> getAllConfig(String keywords);

    /**
     * 删除配置项
     *
     * @param name 脚本名
     */
    void deleteConfig(String name);


}