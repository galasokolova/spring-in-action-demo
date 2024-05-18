package pt.galina.tacocloudapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.galina.tacocloudapi.taco.EmailOrder;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/fromEmail")
    public void processOrderFromEmail(@RequestBody EmailOrder emailOrder) {
        // Обработка заказа из электронной почты
        orderService.processEmailOrder(emailOrder);
    }
}
