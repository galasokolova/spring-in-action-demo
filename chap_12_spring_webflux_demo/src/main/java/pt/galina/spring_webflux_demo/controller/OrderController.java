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

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder order) {
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
    }

    @PostMapping
    public Mono<String> processOrder(@Valid @ModelAttribute TacoOrder order,
                                     Errors errors,
                                     SessionStatus sessionStatus,
                                     @AuthenticationPrincipal User user) {

        log.debug("Processing order: {}", order);

        if (errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return Mono.just("orderForm");
        }
        order.setUser(user);
        return orderRepo.save(order)
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
