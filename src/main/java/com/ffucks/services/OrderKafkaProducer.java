package com.ffucks.services;

import com.ffucks.dtos.OrderCreated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public OrderKafkaProducer(KafkaTemplate<String, String> kafkaTemplate,
                              @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(OrderCreated order) {
        // Converte para JSON simples
        String payload = String.format("{\"orderId\":\"%s\",\"customer\":\"%s\",\"total\":%s}",
                order.orderId(), order.customer(), order.total());
        kafkaTemplate.send(topic, order.orderId(), payload);
    }
}
