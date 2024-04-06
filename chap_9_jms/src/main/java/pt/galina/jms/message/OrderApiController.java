package pt.galina.jms.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.galina.jms.data.OrderRepository;
import pt.galina.jms.dto.TacoDTO;
import pt.galina.jms.dto.TacoOrderDTO;
import pt.galina.jms.entity.taco.Ingredient;
import pt.galina.jms.entity.taco.TacoOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // Здесь можно внести изменения в заказ перед его возвратом, например, обновить статус на "Отправлено" или добавить время отправки.
        // Эти изменения нужно применять к order, затем сохранять через repo.save(order)ВАЖНО: любые изменения в заказе отразить и в DTO, которое возвращается

        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}



