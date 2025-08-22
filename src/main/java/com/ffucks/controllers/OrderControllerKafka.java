package com.ffucks.controllers;

import com.ffucks.dtos.OrderCreated;
import com.ffucks.services.OrderKafkaProducer;
import com.ffucks.services.OrderProducer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderControllerKafka {

    private final OrderProducer jmsProducer;
    private final OrderKafkaProducer kafkaProducer;

    public OrderControllerKafka(OrderProducer jmsProducer, OrderKafkaProducer kafkaProducer) {
        this.jmsProducer = jmsProducer;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/jms")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createJms(@RequestBody @Valid OrderCreated dto) {
        jmsProducer.send(dto);
    }

    @PostMapping("/kafka")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createKafka(@RequestBody @Valid OrderCreated dto) {
        kafkaProducer.send(dto);
    }
}
