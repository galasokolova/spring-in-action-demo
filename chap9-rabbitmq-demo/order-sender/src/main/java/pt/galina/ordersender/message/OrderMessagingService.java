package pt.galina.ordersender.message;

import pt.galina.ordersender.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
