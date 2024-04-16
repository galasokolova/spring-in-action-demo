package pt.galina.rabbitmq_order_sender.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pt.galina.rabbitmq_order_sender.config.OrderProps;
import pt.galina.rabbitmq_order_sender.data.OrderRepository;
import pt.galina.rabbitmq_order_sender.entity.taco.TacoOrder;
import pt.galina.rabbitmq_order_sender.entity.user.User;


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
        setNameIfNull(user, order);
        setStreetIfNull(user, order);
        setCityIfNull(user, order);
        setStateIfNull(user, order);
        setZipIfNull(user, order);

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/orders/orderList";
    }
    @GetMapping("/orderList")
    public String ordersForUser(
            @AuthenticationPrincipal User user,
            Model model) {
        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());

        model.addAttribute(
                "orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable)
        );

        return "orderList";
    }
    private static void setZipIfNull(User user, TacoOrder order) {
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
    }

    private static void setStateIfNull(User user, TacoOrder order) {
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
    }

    private static void setCityIfNull(User user, TacoOrder order) {
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
    }

    private static void setStreetIfNull(User user, TacoOrder order) {
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
    }

    private static void setNameIfNull(User user, TacoOrder order) {
        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
        }
    }
}
