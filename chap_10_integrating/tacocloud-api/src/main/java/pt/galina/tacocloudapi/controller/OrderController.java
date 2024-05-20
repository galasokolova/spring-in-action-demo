package pt.galina.tacocloudapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pt.galina.tacocloudapi.taco.EmailOrder;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public List<EmailOrder> handleOrder(@RequestBody EmailOrder emailOrder) {
        log.info("\uD83D\uDE9B\uD83D\uDE9B\uD83D\uDE9B Received order: {}", emailOrder);
        if (emailOrder != null) {
            orderService.saveOrder(emailOrder);
        }
        return orderService.getAllOrders();
    }

    @GetMapping
    public List<EmailOrder> getAllOrders() {
        return orderService.getAllOrders();
    }
}
