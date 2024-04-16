package pt.galina.rabbitmq_order_sender.message;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.galina.rabbitmq_order_sender.data.OrderRepository;
import pt.galina.rabbitmq_order_sender.dto.TacoOrderDTO;
import pt.galina.rabbitmq_order_sender.entity.taco.TacoOrder;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);


    private final RabbitOrderMessagingService rabbitOrderMessagingService;
    private final OrderRepository repo;
    private final OrderMessagingService messagingService;


    public OrderApiController(RabbitOrderMessagingService rabbitOrderMessagingService, OrderRepository repo, OrderMessagingService messagingService) {
        this.rabbitOrderMessagingService = rabbitOrderMessagingService;
        this.repo = repo;
        this.messagingService = messagingService;
    }

    @PostMapping("/send/{orderId}")
    public ResponseEntity<TacoOrderDTO> sendOrder(@PathVariable Long orderId) {
        Optional<TacoOrder> orderOptional = repo.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.info("Order with ID {} not found", orderId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TacoOrder order = orderOptional.get();
        TacoOrderDTO orderDTO = rabbitOrderMessagingService.convertToDto(order);
        log.info("Order with ID {} has been sent", orderId);

        messagingService.convertAndSendOrder(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}



