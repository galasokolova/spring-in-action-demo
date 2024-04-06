package pt.galina.jms_pull_receiver.message;

import jakarta.annotation.PostConstruct;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import pt.galina.jms_pull_receiver.dto.TacoOrderDTO;

@Component
public class JmsOrderReceiver {
    private final JmsTemplate jms;
    private final KitchenUI ui;

    private boolean receivingEnabled = false;

    public JmsOrderReceiver(JmsTemplate jms, KitchenUI ui) {
        this.jms = jms;
        this.ui = ui;
    }

    public void startReceivingOrders() {
        receivingEnabled = true;
        while (receivingEnabled) {
            TacoOrderDTO orderDTO = (TacoOrderDTO) jms.receiveAndConvert("tacocloud.order.queue");
            if (orderDTO != null) {
                ui.displayOrder(orderDTO);
            } else {
                // Ожидание некоторое время перед повторным опросом очереди
                try {
                    Thread.sleep(2000); // Пауза в 2 сек
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void stopReceivingOrders() {
        receivingEnabled = false;
    }
}
