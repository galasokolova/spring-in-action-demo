package pt.galina.rabbitmq.message;

import pt.galina.rabbitmq.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
