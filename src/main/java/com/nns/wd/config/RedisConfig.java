package com.nns.wd.config;

import com.nns.wd.services.redis.RedisRequestReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Nirav on 09/05/2016.
 */
@Configuration
public class RedisConfig {

    @Bean
    RedisConnectionFactory jedisConnectionFactory() {

        try {
            String redis_url = System.getenv("REDIS_URL");
            JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
            if (redis_url != null) {
                URI redis_uri = new URI(redis_url);
                jedisConnFactory.setHostName(redis_uri.getHost());
                jedisConnFactory.setPort(redis_uri.getPort());
                jedisConnFactory.setPassword(redis_uri.getUserInfo().split(":", 2)[1]);
                jedisConnFactory.setUsePool(true);
            }
            return jedisConnFactory;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("new-request"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisRequestReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    RedisRequestReceiver receiver(CountDownLatch latch) {
        return new RedisRequestReceiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }
}
