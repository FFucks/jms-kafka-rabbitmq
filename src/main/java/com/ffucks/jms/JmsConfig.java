package com.ffucks.jms;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {
    @Bean
    MappingJackson2MessageConverter jacksonJmsMessageConverter(ObjectMapper mapper) {
        var c = new MappingJackson2MessageConverter();
        c.setTargetType(MessageType.TEXT);
        c.setTypeIdPropertyName("_type");
        c.setObjectMapper(mapper);
        return c;
    }
}
