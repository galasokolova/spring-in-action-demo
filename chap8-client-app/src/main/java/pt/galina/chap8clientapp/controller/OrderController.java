package pt.galina.chap8clientapp.controller;

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
import pt.galina.chap8clientapp.config.OrderProps;
import pt.galina.chap8clientapp.data.OrderRepository;
import pt.galina.chap8clientapp.entity.taco.TacoOrder;
import pt.galina.chap8clientapp.entity.user.User;


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

        if (user != null) {
            if (order.getDeliveryName() == null) {
                order.setDeliveryName(user.getFullname()); //todo User is null
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
        } else {
            order.setDeliveryName("");
            order.setDeliveryStreet("");
            order.setDeliveryCity("");
            order.setDeliveryState("");
            order.setDeliveryZip("");
        }

        return "orderForm";
    }


    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        log.debug("Processing order: {}", order);

        if (errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return "orderForm";
        }
        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete();
        log.debug("Order processed successfully, redirecting to /orderList");
        return "redirect:/orders/orderList";
    }

    @GetMapping("/orderList")
    public String ordersForUser(
            @AuthenticationPrincipal User user,
            Model model){
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());

        model.addAttribute(
                "orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }


}
