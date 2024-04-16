package pt.galina.rabbitmq_order_sender.message;

import pt.galina.rabbitmq_order_sender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
