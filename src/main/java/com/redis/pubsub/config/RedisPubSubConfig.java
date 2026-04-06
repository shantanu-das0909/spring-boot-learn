package com.redis.pubsub.config;

import com.redis.pubsub.consumer.NewsConsumer;
import com.redis.pubsub.consumer.SportsConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static com.redis.pubsub.constant.RedisConstant.NEWS_CHANNEL_NAME;
import static com.redis.pubsub.constant.RedisConstant.SPORTS_CHANNEL_NAME;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisMessageListenerContainer config(
            RedisConnectionFactory redisConnectionFactory,
            NewsConsumer newsConsumer,
            SportsConsumer sportsConsumer
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(newsConsumer, new ChannelTopic(NEWS_CHANNEL_NAME));
        container.addMessageListener(sportsConsumer, new ChannelTopic(SPORTS_CHANNEL_NAME));
        return container;
    }
}
