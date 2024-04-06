package pt.galina.jms.message;

import jakarta.jms.Destination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import pt.galina.jms.dto.TacoDTO;
import pt.galina.jms.dto.TacoOrderDTO;
import pt.galina.jms.entity.taco.Ingredient;
import pt.galina.jms.entity.taco.TacoOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class MessagingConfig {
    @Bean
    public Destination orderQueue() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }

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
