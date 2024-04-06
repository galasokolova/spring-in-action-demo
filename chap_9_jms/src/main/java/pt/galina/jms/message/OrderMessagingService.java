package pt.galina.jms.message;

import pt.galina.jms.dto.TacoOrderDTO;
import pt.galina.jms.entity.taco.TacoOrder;

public interface OrderMessagingService {
    void convertAndSendOrder(TacoOrder order);
}
