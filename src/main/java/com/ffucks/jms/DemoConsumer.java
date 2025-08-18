package com.ffucks.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class DemoConsumer {

    @JmsListener(destination = "${app.jms.queue}")
    public void receive(String message) {
        System.out.println("[JMS] Mensagem consumida: " + message);
        // Aqui você processa a mensagem (chama serviço, persiste, etc.)
    }
}
