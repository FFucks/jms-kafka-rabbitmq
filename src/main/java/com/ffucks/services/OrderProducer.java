package com.ffucks.services;


import com.ffucks.dtos.OrderCreated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final JmsTemplate jms;
    private final String queue;

    public OrderProducer(JmsTemplate jms, @Value("${app.jms.queue}") String queue) {
        this.jms = jms;
        this.queue = queue;
    }

    public void send(OrderCreated payload) {
        jms.convertAndSend(queue, payload);
    }
}
