package pt.galina.chap7restfulcontroller.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.galina.chap7restfulcontroller.data.OrderRepository;
import pt.galina.chap7restfulcontroller.data.TacoRepository;
import pt.galina.chap7restfulcontroller.entity.taco.Taco;
import pt.galina.chap7restfulcontroller.entity.taco.TacoOrder;
import pt.galina.chap7restfulcontroller.exception.OrderNotFoundException;

import java.util.Optional;

/*
(p.167 "Spring inAction")
- the following command line shows how you might fetch recently created tacos with curl:
                                $ curl localhost:8080/api/tacos?recent
- or like this, if you prefer HTTPie:
                                http://localhost:8080/api/tacos?recent
 */
@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
//@RequestMapping(path = "/api/tacos", produces = {"application/json", "text/xml"}) // to allow for XML output
@CrossOrigin(origins = "http://tacocloud:8080")
//@CrossOrigin(origins = {"http://tacocloud:8080", "http://tacocloud.com"}) // to specify multiple values
public class TacoController {

    private final TacoRepository tacoRepo;
    private final OrderRepository orderRepository;

    public TacoController(TacoRepository tacoRepo, OrderRepository orderRepository) {
        this.tacoRepo = tacoRepo;
        this.orderRepository = orderRepository;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0,9, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }
/*
          $ curl localhost:8080/api/tacos/2  - to get taco by {id} = 2
 */
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        return optTaco.map(
                        taco -> new ResponseEntity<>(taco, HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long orderId,
                              @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder patchTacoOrder(@PathVariable("orderId") Long orderId,
                                    @RequestBody TacoOrder patch) {

        TacoOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        patchDeliveryNameIfNotNull(patch, order);
        patchDeliveryStreetIfNotNull(patch, order);
        patchDeliveryCityIfNotNull(patch, order);
        patchDeliveryStateIfNotNull(patch, order);
        patchDeliveryZipIfNotNull(patch, order);
        patchCcNumberIfNotNull(patch, order);
        patchCcExpirationIfNotNull(patch, order);
        patchCcCVVIfNotNull(patch, order);

        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundException(orderId);
        }
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderNotFound(OrderNotFoundException ex) {
        return ex.getMessage();
    }

    private static void patchCcCVVIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getCcCVV() != null){
            order.setCcCVV(patch.getCcCVV());
        }
    }

    private static void patchCcExpirationIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getCcExpiration() != null) {
            order.setCcExpiration(patch.getCcExpiration());
        }
    }

    private static void patchCcNumberIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getCcNumber() != null){
            order.setCcNumber(patch.getCcNumber());
        }
    }

    private static void patchDeliveryZipIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getDeliveryZip() != null) {
            order.setDeliveryZip(patch.getDeliveryZip());
        }
    }

    private static void patchDeliveryStateIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getDeliveryState() != null) {
            order.setDeliveryState(patch.getDeliveryState());
        }
    }

    private static void patchDeliveryCityIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getDeliveryCity() != null) {
            order.setDeliveryCity(patch.getDeliveryCity());
        }
    }

    private static void patchDeliveryStreetIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patch.getDeliveryStreet());
        }
    }

    private static void patchDeliveryNameIfNotNull(TacoOrder patch, TacoOrder order) {
        if (patch.getDeliveryName() != null){
            order.setDeliveryName(patch.getDeliveryName());
        }
    }
}
