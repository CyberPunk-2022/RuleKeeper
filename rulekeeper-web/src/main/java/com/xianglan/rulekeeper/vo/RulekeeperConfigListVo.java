package com.xianglan.rulekeeper.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.language.bm.RuleType;

/**
 * 配置返回类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RulekeeperConfigListVo {
    /**
     * 全限定类名(包名+类名)
     * eg:com.xianglan.rulekeeper.core.constant.RulekeeperConstant
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
    private String ruleLogicGraph;

    /**
     * 规则 脚本代码
     */
    private String ruleLogicCode;
}
