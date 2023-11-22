package com.app.lms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.redis.port}")
    private int REDIS_PORT;

    private RedisCacheConfiguration redisCacheConfiguration(int ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(ttl))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean(name = "soeCacheManager")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        log.info("Initializing LMS Redis Cache Manager");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("lms:instructor:getByUuid", redisCacheConfiguration(60 * 24 * 7));
        cacheConfigurations.put("lms:administrator:getByUuid", redisCacheConfiguration(60 * 24 * 7));
        cacheConfigurations.put("lms:student:getByUuid", redisCacheConfiguration(60 * 24 * 7));
        cacheConfigurations.put("lms:course:getByUuid", redisCacheConfiguration(60 * 24 * 7));

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration(120))
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }
}