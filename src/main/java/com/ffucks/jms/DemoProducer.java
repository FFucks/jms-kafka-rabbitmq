package com.ffucks.jms;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class DemoProducer {

    private final JmsTemplate jmsTemplate;
    private final String queueName;

    public DemoProducer(JmsTemplate jmsTemplate,
                        @Value("${app.jms.queue}") String queueName) {
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    public void sendText(String text) {
        jmsTemplate.convertAndSend(queueName, text);
    }
}
