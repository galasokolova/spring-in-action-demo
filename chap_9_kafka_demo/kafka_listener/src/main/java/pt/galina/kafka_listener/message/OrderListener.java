package pt.galina.kafka_listener.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pt.galina.kafka_listener.dto.TacoOrderDTO;

@Component
public class OrderListener {
    private static final Logger log = LoggerFactory.getLogger(KafkaTopicConfig.class);

    private final KitchenUI ui;

    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    @KafkaListener (topics="tacocloud.orders.topic" )
    public void handle(TacoOrderDTO orderDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Received order: {}", orderDTO);
        ui.displayOrder(orderDTO);
    }
}
