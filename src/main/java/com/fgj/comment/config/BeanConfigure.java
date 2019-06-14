package com.fgj.comment.config;

import com.fgj.comment.mybatisplus.pojo.MPaginationInterceptor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class BeanConfigure {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        RedisCache.redisTemplate = redisTemplate;
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("userCommentCache",RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)));
        configurationMap.put("commonCache",RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(600)));
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory).withInitialCacheConfigurations(configurationMap).build();
    }

    /**
     * 这个keyGenerator和redisTemplate的keySerializer是相互独立的
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return (target,method,params)->{
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("[");
            Stream.of(params).forEach(param->sb.append(param.toString()));
            sb.append("]");
            return sb.toString();
        };
    }

    @Bean
    public MPaginationInterceptor paginationInterceptor(){
        MPaginationInterceptor interceptor = new MPaginationInterceptor();
        interceptor.setDialectType("MYSQL");
        return interceptor;
    }
}
