package pt.galina.jms_pull_receiver.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController {
    private final JmsOrderReceiver jmsOrderReceiver;

    public MessageController(JmsOrderReceiver jmsOrderReceiver) {
        this.jmsOrderReceiver = jmsOrderReceiver;
    }

    @GetMapping("/startReceiving")
    public String startReceiving() {
        jmsOrderReceiver.startReceivingOrders();
        return "redirect:/receivedOrder"; // Перенаправляем на страницу с полученными заказами
    }

    @GetMapping("/stopReceiving")
    public String stopReceiving() {
        jmsOrderReceiver.stopReceivingOrders();
        return "redirect:/"; // Перенаправляем на главную страницу
    }
}
