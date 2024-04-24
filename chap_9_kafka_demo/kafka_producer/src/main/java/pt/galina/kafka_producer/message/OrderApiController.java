package pt.galina.kafka_producer.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.galina.kafka_producer.data.OrderRepository;
import pt.galina.kafka_producer.dto.TacoOrderDTO;
import pt.galina.kafka_producer.entity.taco.TacoOrder;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {
    private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);
    private final KafkaOrderMessagingService kafkaOrderMessagingService;
    private final OrderRepository repo;

    public OrderApiController(KafkaOrderMessagingService kafkaOrderMessagingService, OrderRepository repo) {
        this.kafkaOrderMessagingService = kafkaOrderMessagingService;
        this.repo = repo;
    }

    @PostMapping("/send/{orderId}")
    public ResponseEntity<TacoOrderDTO> sendOrder(@PathVariable Long orderId) {
        Optional<TacoOrder> orderOptional = repo.findById(orderId);
        if (orderOptional.isEmpty()) {
            log.info("Order with ID {} not found", orderId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TacoOrder order = orderOptional.get();
        TacoOrderDTO orderDTO = kafkaOrderMessagingService.convertToDto(order);
        try {
            kafkaOrderMessagingService.sendOrder(order);
            log.info("Order with ID {} has been sent", orderId);
            return new ResponseEntity<>(orderDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Failed to send order with ID {}", orderId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


