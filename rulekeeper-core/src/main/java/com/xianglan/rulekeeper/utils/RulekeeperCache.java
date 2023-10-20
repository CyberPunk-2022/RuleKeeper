package com.xianglan.rulekeeper.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地缓存：{"instanceName":"md5(GroovyCode)"}
 */
@Slf4j
public class RulekeeperCache {
    private static final int INITIAL_CACHE_SIZE = 1 << 7;
    private static final Map<String, String> CODE_CACHE = new HashMap(INITIAL_CACHE_SIZE);

    public static void put2CodeCache(String key, String value) {
        CODE_CACHE.put(key, value);
    }

    public static String get2CodeCache(String key) {
        return CODE_CACHE.get(key);
    }

    public static boolean diff(String key, String currentGroovyCode) {
        // 获取当前的groovy脚本加密进行比对
        String currentGroovyCodeMd5 = DigestUtils.md5DigestAsHex(currentGroovyCode.getBytes(StandardCharsets.UTF_8));
        String originGroovyCode = get2CodeCache(key);
        if (StringUtils.hasText(originGroovyCode) && originGroovyCode.equals(currentGroovyCodeMd5)) {
            log.info("Groovy脚本[{}]未发生变更,不编译解析", key);
            return false;
        }
        return true;
    }
}
