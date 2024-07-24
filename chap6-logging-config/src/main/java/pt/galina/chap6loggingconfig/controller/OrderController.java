package pt.galina.chap6loggingconfig.controller;

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
import pt.galina.chap6loggingconfig.config.OrderProps;
import pt.galina.chap6loggingconfig.data.OrderRepository;
import pt.galina.chap6loggingconfig.entity.taco.TacoOrder;
import pt.galina.chap6loggingconfig.entity.user.User;


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

        ifNameIsNull(user, order);
        ifStreetIsNull(user, order);
        ifCityIsNull(user, order);
        ifStateIsNull(user, order);
        ifZipIsNull(user, order);

        return "orderForm";
    }

    private static void ifZipIsNull(User user, TacoOrder order) {
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
    }

    private static void ifStateIsNull(User user, TacoOrder order) {
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
    }

    private static void ifCityIsNull(User user, TacoOrder order) {
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
    }

    private static void ifStreetIsNull(User user, TacoOrder order) {
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
    }

    private static void ifNameIsNull(User user, TacoOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
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
