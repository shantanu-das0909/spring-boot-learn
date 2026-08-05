package com.kafka.producer.config;

import lombok.NonNull;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

//    @Bean
//    public NewTopic createTopic() {
//        return new NewTopic(
//                "customer-event1",
//                3,
//                (short) 1
//        );
//    }

//    @Bean
//    public ProducerFactory<@NonNull String, Object> producerFactory() {
//
//        Map<String, Object> properties = new HashMap<>();
//
//        properties.put(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:29092"
//        );
//
//        properties.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class
//        );
//
//        properties.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JacksonJsonSerializer.class
//        );
//
//        properties.put(
//                "spring.json.type.mapping",
//                "customer:com.kafka.producer.dto.Customer"
//        );
//
//        return new DefaultKafkaProducerFactory<>(properties);
//    }
//
//    @Bean
//    public KafkaTemplate<@NonNull String, @NonNull Object> kafkaTemplate(
//            ProducerFactory<@NonNull String, Object> producerFactory
//    ) {
//        return new KafkaTemplate<>(producerFactory);
//    }
}