package pt.galina.spring_webflux_demo.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pt.galina.spring_webflux_demo.config.OrderProps;
import pt.galina.spring_webflux_demo.data.OrderRepository;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.entity.user.User;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderProps orderProps;

    public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder tacoOrder) {
        if (tacoOrder.getDeliveryName() == null) {
            tacoOrder.setDeliveryName(user.getFullname());
        }
        if (tacoOrder.getDeliveryStreet() == null) {
            tacoOrder.setDeliveryStreet(user.getStreet());
        }
        if (tacoOrder.getDeliveryCity() == null) {
            tacoOrder.setDeliveryCity(user.getCity());
        }
        if (tacoOrder.getDeliveryState() == null) {
            tacoOrder.setDeliveryState(user.getState());
        }
        if (tacoOrder.getDeliveryZip() == null) {
            tacoOrder.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public Mono<String> processOrder(@Valid @ModelAttribute TacoOrder tacoOrder,
                                     Errors errors,
                                     SessionStatus sessionStatus,
                                     @AuthenticationPrincipal User user) {

        log.debug("Processing order: {}", tacoOrder);

        if (errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return Mono.just("orderForm");
        }
        tacoOrder.setUser(user);
        return orderRepo.save(tacoOrder)
                .doOnSuccess(savedOrder -> {
                    sessionStatus.setComplete();
                    log.debug("Order processed successfully, redirecting to /orderList");
                })
                .thenReturn("redirect:/orders/orderList");
    }

    @GetMapping("/orderList")
    public Mono<String> ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());

        return orderRepo.findByUserOrderByPlacedAtDesc(user, pageable)
                .collectList()
                .doOnNext(orders -> model.addAttribute("orders", orders))
                .thenReturn("orderList");
    }

}
