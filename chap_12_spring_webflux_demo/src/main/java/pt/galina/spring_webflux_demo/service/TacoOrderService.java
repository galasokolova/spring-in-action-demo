package pt.galina.spring_webflux_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.galina.spring_webflux_demo.data.OrderRepository;
import pt.galina.spring_webflux_demo.data.UserRepository;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    TacoOrder order = existingOrder != null ? existingOrder : new TacoOrder();
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

    public Mono<TacoOrder> processOrder(String username, TacoOrder order) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    order.setUser(user);
                    return orderRepository.save(order);
                });
    }

    public Flux<TacoOrder> findOrdersForUser(String username, int pageSize) {
        return userRepository.findByUsername(username)
                .flatMapMany(user -> orderRepository.findByUserOrderByPlacedAtDesc(user, PageRequest.of(0, pageSize)));
    }

    public Mono<TacoOrder> save(TacoOrder order) {
        return orderRepository.save(order);
    }
}
