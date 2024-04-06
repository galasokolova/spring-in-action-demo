package pt.galina.taco_kitchen.message.push_message_model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pt.galina.taco_kitchen.dto.TacoOrderDTO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Profile("jms-listener")
public class OrderListener {
    private static final Logger log = LoggerFactory.getLogger(OrderListener.class);
    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrderDTO orderDTO) {
        log.info("Received order: {}", orderDTO);
        ui.displayOrder(orderDTO);
    }
}