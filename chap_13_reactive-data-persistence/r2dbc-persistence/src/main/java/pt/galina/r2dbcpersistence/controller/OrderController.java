package pt.galina.r2dbcpersistence.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import pt.galina.r2dbcpersistence.config.OrderProps;
import pt.galina.r2dbcpersistence.entity.taco.Taco;
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import pt.galina.r2dbcpersistence.entity.taco.data.IngredientRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.OrderRepository;
import pt.galina.r2dbcpersistence.entity.user.AppUser;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderProps orderProps;
    private final IngredientRepository ingredientRepo;

    public OrderController(OrderRepository orderRepo, OrderProps orderProps, IngredientRepository ingredientRepo) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping("/current")
    public Mono<String> orderForm(@AuthenticationPrincipal Mono<AppUser> userMono,
                                  @ModelAttribute TacoOrder order) {
        return userMono.map(user -> {
            if (order.getDeliveryName() == null) {
                order.setDeliveryName(user.getFullname());
            }
            if (order.getDeliveryStreet() == null) {
                order.setDeliveryStreet(user.getStreet());
            }
            if (order.getDeliveryCity() == null) {
                order.setDeliveryCity(user.getCity());
            }
            if (order.getDeliveryState() == null) {
                order.setDeliveryState(user.getState());
            }
            if (order.getDeliveryZip() == null) {
                order.setDeliveryZip(user.getZip());
            }
            return "orderForm";
        });
    }

    @PostMapping
    public Mono<String> processOrder(@Valid TacoOrder order,
                                     Errors errors,
                                     SessionStatus sessionStatus,
                                     @AuthenticationPrincipal Mono<AppUser> userMono) {
        if (errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return Mono.just("orderForm");
        }

        return userMono.flatMap(user -> {
            order.setUser(user);
            order.setPlacedAt(LocalDateTime.now());
            return orderRepo.save(order)
                    .doOnSuccess(savedOrder -> {
                        sessionStatus.setComplete();
                        log.debug("Order processed successfully, redirecting to /orderList");
                    })
                    .then(Mono.just("redirect:/orders/orderList"));
        });
    }

    @GetMapping("/orderList")
    public Mono<String> ordersForUser(@AuthenticationPrincipal Mono<AppUser> userMono, Model model) {
        return userMono.flatMap(user -> {
            int pageSize = orderProps.getPageSize();

            // Collect all orders and tacos, then add them to the model
            return orderRepo.findByUserIdOrderByPlacedAtDesc(user.getId(), PageRequest.of(0, pageSize))
                    .flatMap(order -> {
                        // Get tacos for each order and their ingredients
                        return Flux.fromIterable(order.getTacos())
                                .flatMap(taco -> ingredientRepo.findAllById(taco.getIngredientIds())
                                        .collectList()
                                        .map(ingredients -> {
                                            taco.setIngredients(ingredients);
                                            return taco;
                                        }))
                                .collectList()
                                .map(tacos -> {
                                    order.setTacos(tacos);
                                    return order;
                                });
                    })
                    .collectList()  // Collect all orders into a list
                    .doOnNext(orders -> model.addAttribute("orders", orders))  // Add orders to the model
                    .then(Mono.just("orderList"));  // Return the view name
        });
    }

}
