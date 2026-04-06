package com.redis.pubsub.consumer;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class GenericLiveConsumer implements MessageListener {
    @Override
    public void onMessage(Message message, byte @Nullable [] pattern) {
        String body = new String(message.getBody());
        String channel = new String(message.getChannel());
        log.info("Received Generic News message for pattern {} from channel {} with message : {}", Arrays.toString(pattern), channel, body);
    }
}
