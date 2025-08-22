package com.ffucks.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "demo-group")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("📦 [Kafka] Recebido -> key={}, value={}", record.key(), record.value());
    }
}
