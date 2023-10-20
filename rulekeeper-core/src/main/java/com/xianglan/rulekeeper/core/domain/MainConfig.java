package com.xianglan.rulekeeper.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 主配置
 * eg：{"instanceNames":["com.java3y.hades.core.constant.HadesConstant"],"updateTime":"2023年3月20日10:26:0131"}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainConfig {
    /**
     * 全限定类名(包名+类名)
     * eg:com.xianglan.rulekeeper.core.constant.HadesConstant
     */
    private List<String> instanceNames;

    /**
     * 更新时间
     */
    private Long updateTime;
}
