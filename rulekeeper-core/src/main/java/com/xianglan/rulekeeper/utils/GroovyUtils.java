package com.xianglan.rulekeeper.utils;

import com.google.common.base.Throwables;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
public class GroovyUtils {
    /**
     * Groovy类加载器
     */
    private static final GroovyClassLoader GROOVY_CLASS_LOADER=new GroovyClassLoader();

    public static Class parseClass(String instanceName,String groovyCode){
        Class groovyClass =null;
        try{
            RulekeeperCache.put2CodeCache(instanceName, DigestUtils.md5DigestAsHex(groovyCode.getBytes(StandardCharsets.UTF_8)));
            // 将groovy代码转化为jvm的class
            groovyClass=GROOVY_CLASS_LOADER.parseClass(groovyCode);
        }catch (Exception e){
            log.info("Groovy解析:class=[{}]语法错误，请检查！e:{}", instanceName, Throwables.getStackTraceAsString(e));
        }
        return groovyClass;
    }
}
