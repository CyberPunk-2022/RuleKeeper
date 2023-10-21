package com.xianglan.rulekeeper.domain;

import com.alibaba.fastjson2.JSONObject;
import com.xianglan.rulekeeper.enums.RuleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * rulekeeper web入参
 *
 * @author 3y
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RulekeeperParam {

    /**
     * 全限定类名(包名+类名)
     * eg:com.java3y.rulekeeper.core.constant.RulekeeperConstant
     */
    private String name;

    /**
     * 规则类型
     *
     * @see RuleType
     */
    private String ruleType;

    /**
     * 规则 逻辑图
     */
    private JSONObject ruleLogicGraph;

    /**
     * 规则 脚本代码
     */
    private String ruleLogicCode;


    /**
     * 搜索条件：关键词
     */
    private String keywords;


}