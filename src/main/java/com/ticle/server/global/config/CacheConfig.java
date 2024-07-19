package com.ticle.server.global.config;

import com.ticle.server.user.redis.CacheNames;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class CacheConfig {


    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory connectionFactory, ResourceLoader resourceLoader) {
        RedisCacheConfiguration defaultConfig
                = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())// value Serializer 변경
                );

        //disableCachingNullValues()를 호출하여 null 값 캐싱을 비활성화합니다. 이는 Redis에 null 값을 저장하지 않도록 설정합니다.
        //serializeValuesWith()를 호출하여 값 직렬화를 구성합니다.
        // 위의 코드에서는 GenericJackson2JsonRedisSerializer를 사용하여 값 직렬화를 수행합니다.
        // 이는 값이 JSON 형식으로 직렬화되어 Redis에 저장되고 검색됩니다.
        //구성이 완료된 RedisCacheConfiguration을 사용하여 RedisCacheManager를 생성합니다.
        //이렇게 구성된 RedisCacheManager는 Spring Boot 애플리케이션에서 Redis 캐시를 관리하는 데 사용됩니다.
        // 캐시 설정과 Redis 연결을 제어하고 캐시 작업을 수행할 때 RedisCacheManager를 주입받아 사용할 수 있다.

        //redisCacheConfigMap은 Redis 캐시의 구성 정보를 담는 맵입니다.
        Map<String, RedisCacheConfiguration> redisCacheConfigMap
                = new HashMap<>();

        redisCacheConfigMap.put(
                CacheNames.USERBYEMAIL,
                defaultConfig.entryTtl(Duration.ofHours(4)) //entryTtl()을 호출하여 캐시 항목의 만료 시간(TTL)을 설정합니다.  캐시 수명 4시간
        );

        // ALLUSERS에 대해서만 다른 Serializer 적용
        redisCacheConfigMap.put(
                CacheNames.ALLUSERS,
                defaultConfig.entryTtl(Duration.ofHours(4))
                        .serializeValuesWith( //serializeValuesWith()를 호출하여 값을 직렬화하는 방식을 설정합니다.
                                RedisSerializationContext
                                        .SerializationPair
                                        .fromSerializer(new JdkSerializationRedisSerializer())
                        )
        );
        redisCacheConfigMap.put(
                CacheNames.LOGINUSER,
                defaultConfig.entryTtl(Duration.ofHours(2))
        );


        return RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(redisCacheConfigMap)
                .build();
    }

}
