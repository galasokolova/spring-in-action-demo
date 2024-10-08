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
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import pt.galina.r2dbcpersistence.entity.taco.data.IngredientRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.OrderRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.TacoRepository;
import pt.galina.r2dbcpersistence.entity.user.AppUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderProps orderProps;
    private final TacoRepository tacoRepo;
    private final IngredientRepository ingredientRepo;

    public OrderController(OrderRepository orderRepo, OrderProps orderProps, TacoRepository tacoRepo, IngredientRepository ingredientRepo) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepo;
    }


    @GetMapping("/current")
    public Mono<String> orderForm(@AuthenticationPrincipal Mono<AppUser> userMono,
                                  @ModelAttribute TacoOrder order, Model model) {
        return userMono.flatMap(user -> {
            // Проверьте и установите данные доставки, если они отсутствуют
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

            return Flux.fromIterable(order.getTacoIds())
                    .flatMap(tacoRepo::findById)
                    .flatMap(taco -> ingredientRepo.findAllById(taco.getIngredientIds())
                            .collectList()
                            .map(ingredients -> {
                                taco.setIngredients(ingredients);
                                return taco;
                            }))
                    .collectList()
                    .doOnNext(tacos -> model.addAttribute("tacos", tacos))
                    .then(Mono.just("orderForm"));
        });
    }

    @PostMapping
    public Mono<String> processOrder(@Valid TacoOrder order,
                                     Errors errors,
                                     SessionStatus sessionStatus,
                                     @AuthenticationPrincipal Mono<AppUser> userMono) {

        log.debug("\uD83C\uDF1F Processing order: {}", order);

        if (errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return Mono.just("orderForm");
        }

        // Добавление логики сохранения заказа
        return userMono.flatMap(user -> {
            order.setUser(user);
            order.setPlacedAt(LocalDateTime.now());

            return orderRepo.save(order)
                    .doOnSuccess(savedOrder -> {
                        sessionStatus.setComplete();
                        log.debug("\uD83C\uDF1F Order processed successfully, redirecting to /orderList");
                    })
                    .then(Mono.just("redirect:/orders/orderList"));
        });
    }


    @GetMapping("/orderList")
    public Mono<String> ordersForUser(@AuthenticationPrincipal Mono<AppUser> userMono, Model model) {
        return userMono.flatMap(user -> {
            int pageSize = orderProps.getPageSize();

            return orderRepo.findByUserIdOrderByPlacedAtDesc(user.getId(), PageRequest.of(0, pageSize))
                    .flatMap(order -> {
                        return Flux.fromIterable(order.getTacoIds())
                                .flatMap(tacoRepo::findById)
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
                    .collectList()
                    .doOnNext(orders -> model.addAttribute("orders", orders))
                    .then(Mono.just("orderList"));
        });
    }
}
