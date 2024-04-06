package pt.galina.jms_push_listener.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.galina.jms_push_listener.dto.TacoOrderDTO;

import java.util.List;


@Controller
public class OrderReceiverController {
    private static final Logger log = LoggerFactory.getLogger(OrderReceiverController.class);
    private final KitchenUI ui;

    public OrderReceiverController(KitchenUI ui) {
        this.ui = ui;
    }

    @GetMapping("/receivedOrder")
    public String showAllReceivedOrders(Model model) {
        List<TacoOrderDTO> allReceivedOrders = ui.getAllOrders(); // предполагаем, что этот метод существует и возвращает список всех полученных заказов в формате DTO

        if (!allReceivedOrders.isEmpty()) {
            model.addAttribute("orders", allReceivedOrders);
            return "receivedOrder"; // имя шаблона Thymeleaf для отображения всех заказов
        } else {
            log.info("No orders have been received");
            return "noOrderFound"; // предполагаем, что страница noOrdersFound.html существует для ситуации, когда заказы не были получены
        }
    }
}
