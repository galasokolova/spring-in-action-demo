package pt.galina.chap_17_jmxmonitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.galina.chap_17_jmxmonitoring.entity.taco.TacoOrder;
import pt.galina.chap_17_jmxmonitoring.entity.taco.data.OrderRepository;
import pt.galina.chap_17_jmxmonitoring.entity.user.data.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class TacoOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public TacoOrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Mono<TacoOrder> findOrCreateOrder(String username, TacoOrder existingOrder) {
        log.info("🔍 findOrCreateOrder called with username: {}", username);
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    log.info("🔍 Found user: {}", user);
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
                    log.info("⏩⏩⏩ Autofilled order: {}", order);
                    return Mono.just(order);
                });
    }


    public Flux<TacoOrder> findOrdersForUser(String username, int pageSize) {
        log.info("🔍 Finding orders for user: {}", username);
        return userRepository.findByUsername(username)
                .flatMapMany(user -> orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, pageSize))
                        .doOnNext(order -> log.info("🔍 Found order: {}", order)));
    }



    public Mono<TacoOrder> processOrder(String username, TacoOrder order) {
        return findOrCreateOrder(username, order)
                .flatMap(orderToSave -> {
                    orderToSave.placedAt();
                    log.info("▶️▶️▶️Processing order: {}", orderToSave);
                    return save(orderToSave);
                });
    }

    public Mono<TacoOrder> save(TacoOrder order) {
        return orderRepository.save(order)
                .doOnSuccess(savedOrder -> log.info("🌟🌟🌟 Saved order: {}", savedOrder))
                .doOnError(error -> log.error("❌❌❌ Failed to save order: {}", error.getMessage()));
    }

}
