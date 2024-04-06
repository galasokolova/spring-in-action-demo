package pt.galina.taco_kitchen.message.pull_message_model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import pt.galina.taco_kitchen.dto.TacoOrderDTO;
import pt.galina.taco_kitchen.entity.taco.TacoOrder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {

    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("order", TacoOrderDTO.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }
}
