//package org.example.websocket.configuration;
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.web.socket.WebSocketSession;
//
//@Configuration
//@EnableCaching
//public class RedisConfiguration {
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(){
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName("localhost");
//        configuration.setPort(6379);
//        return new JedisConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisTemplate<String, WebSocketSession> redisTemplate() {
//        RedisTemplate<String, WebSocketSession> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        return redisTemplate;
//    }
//
//}
