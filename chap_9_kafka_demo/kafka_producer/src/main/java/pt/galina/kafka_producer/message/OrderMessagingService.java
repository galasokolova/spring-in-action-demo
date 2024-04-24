package pt.galina.kafka_producer.message;

import pt.galina.kafka_producer.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
