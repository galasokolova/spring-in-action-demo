package pt.galina.chap_18_googlecloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import pt.galina.chap_18_googlecloud.entity.taco.Ingredient;
import pt.galina.chap_18_googlecloud.entity.taco.Taco;
import pt.galina.chap_18_googlecloud.entity.taco.TacoOrder;
import pt.galina.chap_18_googlecloud.entity.taco.data.OrderRepository;
import pt.galina.chap_18_googlecloud.entity.user.data.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TacoOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final IngredientService ingredientService;
    private final Validator validator;

    @Autowired
    public TacoOrderService(OrderRepository orderRepository, UserRepository userRepository, IngredientService ingredientService, Validator validator) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
        this.validator = validator;
    }

    public Mono<TacoOrder> findOrCreateOrder(String username, TacoOrder existingOrder) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    TacoOrder order = existingOrder != null ? existingOrder : new TacoOrder();
                    order.setUser(user);
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
                    return Mono.just(order);
                });
    }

    public Flux<TacoOrder> findOrdersForUser(String username, int pageSize) {
        return userRepository.findByUsername(username)
                .flatMapMany(user -> orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, pageSize))
                        .doOnNext(order -> log.info("🔍 Found order: {}", order)));
    }

    public Mono<TacoOrder> processOrder(String username, TacoOrder order) {
        return findOrCreateOrder(username, order)
                .flatMap(orderToSave -> {
                    BindingResult bindingResult = new BeanPropertyBindingResult(orderToSave, "tacoOrder");
                    validator.validate(orderToSave, bindingResult);

                    if (bindingResult.hasErrors()) {
                        return Mono.error(new IllegalArgumentException("Order fields cannot be null or invalid"));
                    }

                    orderToSave.placedAt();
                    return save(orderToSave);
                });
    }

    public Mono<TacoOrder> save(TacoOrder order) {
        return orderRepository.save(order)
                .doOnSuccess(savedOrder -> log.info("🌟 Saved order: {}", savedOrder))
                .doOnError(error -> log.error("❌ Failed to save order: {}", error.getMessage()));
    }

    public Mono<Map<String, Object>> createModelForDesign() {
        return ingredientService.findAll()
                .collectList()
                .map(ingredients -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("taco", new Taco());

                    Map<Ingredient.Type, List<Ingredient>> ingredientsByType = ingredients.stream()
                            .collect(Collectors.groupingBy(Ingredient::getType));

                    ingredientsByType.forEach((type, ingredientList) ->
                            model.put(type.toString().toLowerCase(), ingredientList)
                    );

                    return model;
                });
    }

    public boolean isCompleted(TacoOrder order) {
        return order != null && order.getCcNumber() != null && order.getCcExpiration() != null && order.getCcCVV() != null;
    }
}
