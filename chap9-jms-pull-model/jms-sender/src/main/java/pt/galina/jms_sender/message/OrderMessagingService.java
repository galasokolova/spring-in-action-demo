package pt.galina.jms_sender.message;

import pt.galina.jms_sender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
