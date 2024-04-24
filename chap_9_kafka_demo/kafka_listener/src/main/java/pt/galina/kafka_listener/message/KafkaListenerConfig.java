package pt.galina.kafka_listener.message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import pt.galina.kafka_listener.dto.TacoOrderDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaListenerConfig {
    private static final Logger log = LoggerFactory.getLogger(KafkaListenerConfig.class);

    @Bean
    public ConsumerFactory<String, TacoOrderDTO> consumerFactory() {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Creating ConsumerFactory with configuration...");
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "tacocloud-listeners");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        JsonDeserializer<TacoOrderDTO> deserializer = new JsonDeserializer<>(TacoOrderDTO.class);
        deserializer.addTrustedPackages("*");

        DefaultKafkaConsumerFactory<String, TacoOrderDTO> factory = new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ConsumerFactory created successfully.");
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> kafkaListenerContainerFactory() {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Creating KafkaListenerContainerFactory...");
        ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>KafkaListenerContainerFactory created successfully.");
        return factory;
    }
}


