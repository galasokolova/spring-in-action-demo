package pt.galina.jms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pt.galina.jms.data.OrderRepository;
import pt.galina.jms.entity.taco.TacoOrder;
import pt.galina.jms.service.OrderMessagingService;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

    private final OrderRepository repo;
    private final OrderMessagingService messagingService;


    public OrderApiController(OrderRepository repo, OrderMessagingService messagingService) {
        this.repo = repo;
        this.messagingService = messagingService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        messagingService.sendOrder(order);
        return repo.save(order);
    }
}



