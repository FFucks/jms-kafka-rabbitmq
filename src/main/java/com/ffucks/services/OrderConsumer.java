package com.ffucks.services;


import com.ffucks.dtos.OrderCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @JmsListener(destination = "${app.jms.queue}")
    public void onMessage(OrderCreated msg) {
        log.info("✅ Recebido: id={}, cliente={}, total={}",
                msg.orderId(), msg.customer(), msg.total());
    }
}
