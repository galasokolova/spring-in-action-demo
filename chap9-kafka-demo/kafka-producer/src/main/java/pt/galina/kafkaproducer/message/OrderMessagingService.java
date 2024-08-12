package pt.galina.kafkaproducer.message;

import pt.galina.kafkaproducer.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
