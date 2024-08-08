package pt.galina.jmsordersender.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.galina.jmsordersender.data.OrderRepository;
import pt.galina.jmsordersender.dto.TacoOrderDTO;
import pt.galina.jmsordersender.entity.taco.TacoOrder;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);


    private final JmsOrderMessagingService jms;
    private final OrderRepository repo;
    private final OrderMessagingService messagingService;

    public OrderApiController(JmsOrderMessagingService jms, OrderRepository repo, OrderMessagingService messagingService) {
        this.jms = jms;
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
        TacoOrderDTO orderDTO = jms.convertToDto(order);
        log.info("Order with ID {} has been sent", orderId);

        messagingService.convertAndSendOrder(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}



