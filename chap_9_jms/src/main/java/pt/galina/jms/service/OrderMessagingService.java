package pt.galina.jms.service;

import pt.galina.jms.entity.taco.TacoOrder;

public interface OrderMessagingService {

    void sendOrder(TacoOrder order);
    void convertAndSendOrder(TacoOrder order);
}
