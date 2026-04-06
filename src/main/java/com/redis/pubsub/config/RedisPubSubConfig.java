package com.redis.pubsub.config;

import com.redis.pubsub.consumer.GenericLiveConsumer;
import com.redis.pubsub.consumer.NewsConsumer;
import com.redis.pubsub.consumer.SportsConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static com.redis.pubsub.constant.RedisConstant.*;

@Configuration
public class RedisPubSubConfig {

    @Bean
    public RedisMessageListenerContainer config(
            RedisConnectionFactory redisConnectionFactory,
            NewsConsumer newsConsumer,
            SportsConsumer sportsConsumer,
            GenericLiveConsumer genericLiveConsumer
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(genericLiveConsumer, new PatternTopic(GENERIC_LIVE_PATTERN));
//        container.addMessageListener(newsConsumer, new ChannelTopic(NEWS_CHANNEL_NAME));
//        container.addMessageListener(sportsConsumer, new ChannelTopic(SPORTS_CHANNEL_NAME));
        return container;
    }
}
