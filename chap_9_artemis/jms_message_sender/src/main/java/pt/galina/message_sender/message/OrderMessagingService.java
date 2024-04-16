package pt.galina.message_sender.message;

import pt.galina.message_sender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
