package com.fgj.comment.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "system")
@Component
@Data
public class SystemProperties {
    @Data
    public static class Security{
        private String tokenName;
    }
    @Data
    public static class RedisKey{
        private String tokenKeyPrefix;
        public String generateTokenKey(String token){
            return tokenKeyPrefix + token;
        }
    }
    private Security security;
    private RedisKey redisKey;
}
