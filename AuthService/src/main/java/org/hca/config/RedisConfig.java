package org.hca.config;

import lombok.RequiredArgsConstructor;
import org.hca.entity.AppUser;
import org.hca.entity.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {
    @Value("${redis.host}")
    String redisHost;
    @Value("${redis.port}")
    int redisPort;
    private final JacksonConfig jacksonConfig;
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost,redisPort));
    }
    @Bean
    public RedisTemplate<String, AppUser> appUserRedisTemplate() {
        RedisTemplate<String,AppUser> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
//    @Bean
//    public RedisTemplate<String, Token> tokenRedisTemplate() {
//        RedisTemplate<String,Token> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return redisTemplate;
//    }

    @Bean
    public RedisTemplate<String, Token> tokenRedisTemplate() {
        RedisTemplate<String, Token> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(jacksonConfig.objectMapper());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
