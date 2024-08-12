package pt.galina.kafkalistener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "your-topic" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles("test")  // Ensure that the test profile is active
class KafkaListenerApplicationTests {

    @Test
    void contextLoads() {
    }

}
