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
import pt.galina.r2dbcpersistence.entity.taco.web.TacoOrderAggregateService;
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
    private final TacoOrderAggregateService tacoOrderAggregateService;
    private final TacoRepository tacoRepo;
    private final IngredientRepository ingredientRepo;

    public OrderController(OrderRepository orderRepo, OrderProps orderProps, TacoOrderAggregateService tacoOrderAggregateService, TacoRepository tacoRepo, IngredientRepository ingredientRepo) {
        this.orderRepo = orderRepo;
        this.orderProps = orderProps;
        this.tacoOrderAggregateService = tacoOrderAggregateService;
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

            // Загрузить список Taco по их идентификаторам и передать в модель
            return Flux.fromIterable(order.getTacoIds())
                    .flatMap(tacoRepo::findById)
                    .flatMap(taco -> ingredientRepo.findAllById(taco.getIngredientIds())
                            .collectList()
                            .map(ingredients -> {
                                taco.setIngredients(ingredients);
                                return taco;
                            }))
                    .collectList()
                    .doOnNext(tacos -> model.addAttribute("tacos", tacos)) // Добавляем список тако в модель
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

            // Сохранение заказа (без полной логики извлечения всех такосов и ингредиентов)
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

            // Извлечение всех заказов пользователя
            return orderRepo.findByUserIdOrderByPlacedAtDesc(user.getId(), PageRequest.of(0, pageSize))
                    .flatMap(order -> {
                        // Извлечение всех такосов для каждого заказа
                        return Flux.fromIterable(order.getTacoIds())
                                .flatMap(tacoRepo::findById)
                                .flatMap(taco -> ingredientRepo.findAllById(taco.getIngredientIds())
                                        .collectList()
                                        .map(ingredients -> {
                                            taco.setIngredients(ingredients);  // Присвоение ингредиентов каждому такос
                                            return taco;
                                        }))
                                .collectList()  // Собираем все такосы для данного заказа
                                .map(tacos -> {
                                    order.setTacos(tacos);  // Присваиваем такосы заказу
                                    return order;
                                });
                    })
                    .collectList()  // Собираем все заказы в список
                    .doOnNext(orders -> model.addAttribute("orders", orders))  // Добавляем заказы в модель
                    .then(Mono.just("orderList"));  // Возвращаем название представления
        });
    }



}
