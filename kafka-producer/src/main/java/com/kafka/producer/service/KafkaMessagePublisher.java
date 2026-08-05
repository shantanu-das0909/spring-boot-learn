package com.kafka.producer.service;

import com.kafka.producer.dto.Customer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {

    private final KafkaTemplate<@NonNull String, @NonNull Object> template;

    public void sendMessage(String message) {
        CompletableFuture<SendResult<@NonNull String, @NonNull Object>> future = template.send("route-topic", 3, "route-topic-key", message);
        future.whenComplete((result, ex) -> {
            if(ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send [{}] due to: {}", message, ex.getMessage());
            }
        });
    }

    public void sendEvent(Customer customer) {

        try {
            CompletableFuture<SendResult<@NonNull String, @NonNull Object>> future = template.send("error-handler-topic", customer);
            future.whenComplete((result, ex) -> {
                if(ex == null) {
                    log.info("Sent the event: {} with offset: {}", customer.toString(), result.getRecordMetadata().offset());
                } else {
                    log.error("Could not sent the event: {}", customer.toString());
                }
            });
        } catch(Exception e) {
            log.error("Could not send due to internal error: {}", e.getMessage());
        }

    }
}
