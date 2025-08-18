package com.ffucks.controllers;

import com.ffucks.dtos.MessageRequest;
import com.ffucks.jms.DemoProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final DemoProducer producer;

    public MessageController(DemoProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> publish(@RequestBody MessageRequest body) {
        producer.sendText(body.text());
        return ResponseEntity.ok("Mensagem enviada: " + body.text());
    }
}
