package pt.galina.orderreceiver.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.galina.orderreceiver.dto.TacoOrderDTO;
import pt.galina.orderreceiver.message.KitchenUI;

@Component
public class OrderListener {
    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(TacoOrderDTO orderDTO) {
        ui.displayOrder(orderDTO);
    }
}
