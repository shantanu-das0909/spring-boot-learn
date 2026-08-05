package com.kafka.producer.controller;

import com.kafka.producer.dto.Customer;
import com.kafka.producer.service.KafkaMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
@RequiredArgsConstructor
public class EventController {

    private final KafkaMessagePublisher kafkaMessagePublisher;
    private final KafkaTemplate<String, Object> template;

    @GetMapping("/publish")
    public ResponseEntity<?> publicMessage(@RequestParam String message) {
        try {
//            for(int i = 0; i < 100; i++) {
//                kafkaMessagePublisher.sendMessage(message + ": " + i);
//            }
            template.send("route-topic", 3, "route-topic-key", message);
            template.send("route-topic", 2, "route-topic-key", message);
            template.send("route-topic", 3, "route-topic-key", message);
            template.send("route-topic", 2, "route-topic-key", message);
            template.send("route-topic", 1, "route-topic-key", message);

            return ResponseEntity.ok("Message published successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publishEvents(@RequestBody Customer customer) {
        try {
//            for(int i = 0; i < 10000; i++) {
//                customer.setId(String.valueOf(i));
//                kafkaMessagePublisher.sendEvent(customer);
//            }
            kafkaMessagePublisher.sendEvent(customer);
            return ResponseEntity.ok("Message published successfully");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
