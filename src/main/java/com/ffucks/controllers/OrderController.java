package com.ffucks.controllers;


import com.ffucks.dtos.OrderCreated;
import com.ffucks.services.OrderProducer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void create(@RequestBody @Valid OrderCreated dto) {
        producer.send(dto);
    }
}
