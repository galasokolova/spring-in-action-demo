package pt.galina.push_message_listener.message;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pt.galina.push_message_listener.dto.TacoOrderDTO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class KitchenUI {
    private static final Logger log = LoggerFactory.getLogger(KitchenUI.class);

    private final List<TacoOrderDTO> orders = new CopyOnWriteArrayList<>();

    public void displayOrder(TacoOrderDTO orderDTO) {
        orders.add(orderDTO);
        log.info("->>>>>> All orders: {}", orders);
    }

    public List<TacoOrderDTO> getAllOrders() {
        return orders.stream().toList();
    }
}
