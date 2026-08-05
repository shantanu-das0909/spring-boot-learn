package com.kafka.consumer.config;

import lombok.NonNull;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class KafkaConsumerConfig {

//    @Bean
//    public ConsumerFactory<@NonNull String, Object> consumerFactory() {
//        Map<String, Object> properties = new HashMap<>();
//
//        properties.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:29092"
//        );
//
//        properties.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                "route-topic post-group"
//        );
//
//        properties.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class
//        );
//
//        properties.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JacksonJsonDeserializer.class
//        );
//
//        properties.put(
//                "spring.json.type.mapping",
//                "customer:com.kafka.consumer.dto.Customer"
//        );
//
//        properties.put(
//                "spring.json.trusted.packages",
//                "com.kafka.consumer.dto"
//        );
//
//        return new DefaultKafkaConsumerFactory<>(properties);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull Object> kafkaListenerContainerFactory(ConsumerFactory<@NonNull String, Object> consumerFactory) {
//        var factory = new ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull Object>();
//
//        factory.setConsumerFactory(consumerFactory);
//
//        return factory;
//    }
}
