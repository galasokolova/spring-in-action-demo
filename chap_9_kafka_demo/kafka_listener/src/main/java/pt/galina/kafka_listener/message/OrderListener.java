package pt.galina.kafka_listener.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pt.galina.kafka_listener.dto.TacoOrderDTO;

@Service
public class OrderListener {
    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @KafkaListener (topics="tacocloud.orders.topic", groupId = "tacocloud-listeners")
    public void handle(TacoOrderDTO orderDTO) {
        ui.displayOrder(orderDTO);
    }
}
