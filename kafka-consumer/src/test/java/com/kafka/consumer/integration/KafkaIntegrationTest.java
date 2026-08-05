package com.kafka.consumer.integration;


import com.kafka.consumer.dto.Customer;
import com.kafka.consumer.service.KafkaMessageListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.NonNull;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class KafkaIntegrationTest {

    @Container
    private static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka:4.1.0")
    );

    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<@NotNull String, @NotNull Object> producer;

    @Autowired
    private KafkaMessageListener kafkaMessageListener;

    @Test
    public void testConsume() {
        Customer customer = Customer.builder()
                .id("123")
                .name("IMU")
                .email("imu@gmail.com")
                .contact("1234567890")
                .build();

            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>("route-topic", customer);
            producer.send(producerRecord);

            await()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(100))
                .untilAsserted(() -> {
                    // assert statement
                    assertThat(kafkaMessageListener.getCustomerList().size()).isEqualTo(1);
                });
    }


}
