//package pt.galina.kafkalistener.message;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import pt.galina.kafkalistener.deserialization.JsonPOJODeserializer;
//import pt.galina.kafkalistener.dto.TacoOrderDTO;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaConsumerConfig {
//
//    @Bean
//    public ConsumerFactory<String, TacoOrderDTO> consumerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
//        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "tacocloud-listeners");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        // Используем кастомный десериализатор для значений
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonPOJODeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonPOJODeserializer<>(TacoOrderDTO.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
//}
//
//
package pt.galina.kafkalistener.message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import pt.galina.kafkalistener.deserialization.JsonPOJODeserializer;
import pt.galina.kafkalistener.dto.TacoOrderDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, TacoOrderDTO> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonPOJODeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonPOJODeserializer<>(TacoOrderDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TacoOrderDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
