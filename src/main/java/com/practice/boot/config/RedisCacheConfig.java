package com.practice.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@EnableCaching  //开启缓存支持
public class RedisCacheConfig extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    /**
     * 自定义key生成器
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return (o,method,params) ->{
            StringBuilder sb = new StringBuilder();
            //类名
            sb.append(o.getClass().getName());
            //方法名
            sb.append(method.getName());
            //参数名
            for (Object param:params) {
                sb.append(param);
            }
            return sb.toString();
        };
    }

    /**
     * 缓存管理器
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration redisCache = RedisCacheConfiguration.defaultCacheConfig()
                //60秒缓存失效
                .entryTtl(Duration.ofSeconds(60))
                //设置key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                //不缓存null值
                .disableCachingNullValues();
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCache)
                .transactionAware()
                .build();
        logger.info("自定义RedisCacheManager加载完成");
        return  redisCacheManager;
    }


    /**
     * key键序列化方式
     * @return
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * value值序列化方式
     * @return
     */
    private GenericJackson2JsonRedisSerializer valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
