package pt.galina.jms_order_sender.message;

import pt.galina.jms_order_sender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
