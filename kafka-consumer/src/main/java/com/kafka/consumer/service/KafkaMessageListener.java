package com.kafka.consumer.service;

import com.kafka.consumer.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

@Slf4j
@Service
public class KafkaMessageListener {

    private final List<Customer> customers = new ArrayList<>();

    @RetryableTopic(
            attempts = "4",
            numPartitions = "1",
            replicationFactor = "1",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backOff = @BackOff(
                    delay = 2000,
                    multiplier = 2
            )
    )
    @KafkaListener(topics = "error-handler-topic", groupId = "error-handler-group")
    public void consumeMessage(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(customer), topic, offset);
        List<String> restrictedContacts = List.of("1111111111", "2222222222");

        if(restrictedContacts.contains(customer.getContact())) {
            throw new RuntimeException("Invalid contacts received!");
        }
    }

    @DltHandler
    public void dltHandler(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("DLT message received: {} from {} offset {}", customer.getName(), topic, offset);
    }

//    @KafkaListener(topics = "route-topic", groupId = "route-topic-group")
//    public void consume(Customer customer) {
//        customers.add(customer);
//        log.info("route-topic topic. Consumer 1: {}", customer.toString());
//    }
//
//    public List<Customer> getCustomerList() {
//        return customers;
//    }

//    @KafkaListener(groupId = "route-topic-group", topicPartitions = {@TopicPartition(topic = "route-topic", partitions = {"2"})})
//    public void consume(String message) {
//        log.info("customer-event topic. Consumer: {}", message);
//    }



//
//    @KafkaListener(topics = "bulk-upload", groupId = "bulk-upload-group2")
//    public void consume2(String message) {
//        log.info("Bulk upload topic. Consumer 2: {}", message);
//    }
//
//    @KafkaListener(topics = "bulk-upload", groupId = "bulk-upload-group2")
//    public void consume3(String message) {
//        log.info("Bulk upload topic. Consumer 3: {}", message);
//    }
//
//    @KafkaListener(topics = "bulk-upload", groupId = "bulk-upload-group2")
//    public void consume4(String message) {
//        log.info("Bulk upload topic. Consumer 4: {}", message);
//    }
}
