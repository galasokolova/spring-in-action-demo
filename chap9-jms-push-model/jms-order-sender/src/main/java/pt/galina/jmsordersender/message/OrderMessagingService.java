package pt.galina.jmsordersender.message;

import pt.galina.jmsordersender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
