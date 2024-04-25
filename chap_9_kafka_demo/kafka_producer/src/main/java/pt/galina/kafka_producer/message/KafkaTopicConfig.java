package pt.galina.kafka_producer.message;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
//        return new KafkaAdmin(configs);
//    }

    @Bean
    public NewTopic exampleTopic() {
        return new NewTopic("tacocloud.orders.topic", 2, (short) 1); // имя, количество партиций, фактор репликации
    }
}