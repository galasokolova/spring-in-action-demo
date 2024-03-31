package pt.galina.jms.service;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pt.galina.jms.entity.taco.TacoOrder;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{
    private final JmsTemplate jms;
    private final Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(TacoOrder order) {

        jms.send(
                orderQueue,
                session -> session.createObjectMessage(order)
        );
    }

    @Override
    public void convertAndSendOrder(TacoOrder order) {
        jms.convertAndSend("tacocloud.order.queue", order, JmsOrderMessagingService::addOrderSource);
    }

    private static Message addOrderSource(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
        return message;
    }
}
