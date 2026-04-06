package com.redis.pubsub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.redis.pubsub.constant.RedisConstant.NEWS_CHANNEL_NAME;
import static com.redis.pubsub.constant.RedisConstant.SPORTS_CHANNEL_NAME;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NewsPublisher {

    private final StringRedisTemplate redisTemplate;

    @GetMapping
    public void publish(@RequestParam String newsMessage, @RequestParam String sportsMessage) {
        redisTemplate.convertAndSend(NEWS_CHANNEL_NAME, newsMessage);
        redisTemplate.convertAndSend(SPORTS_CHANNEL_NAME, sportsMessage);
    }
}
