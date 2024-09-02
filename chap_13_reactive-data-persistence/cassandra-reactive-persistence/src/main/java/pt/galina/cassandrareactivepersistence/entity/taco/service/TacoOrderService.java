package pt.galina.cassandrareactivepersistence.entity.taco.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.galina.cassandrareactivepersistence.entity.taco.TacoOrder;
import pt.galina.cassandrareactivepersistence.entity.taco.data.OrderRepository;
import pt.galina.cassandrareactivepersistence.entity.user.User;
import pt.galina.cassandrareactivepersistence.entity.user.UserUDT;
import pt.galina.cassandrareactivepersistence.entity.user.data.UserRepository;
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
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    UserUDT userUDT = user.getAddress();  // Получаем адрес из UserUDT

                    TacoOrder order = existingOrder != null ? existingOrder : new TacoOrder();
                    order.setUser(userUDT);

                    if (order.getDeliveryName() == null) {
                        order.setDeliveryName(user.getFullname());
                    }
                    if (order.getDeliveryStreet() == null && userUDT != null) {
                        order.setDeliveryStreet(userUDT.getStreet());
                    }
                    if (order.getDeliveryCity() == null && userUDT != null) {
                        order.setDeliveryCity(userUDT.getCity());
                    }
                    if (order.getDeliveryState() == null && userUDT != null) {
                        order.setDeliveryState(userUDT.getState());
                    }
                    if (order.getDeliveryZip() == null && userUDT != null) {
                        order.setDeliveryZip(userUDT.getZip());
                    }

                    return Mono.just(order);
                });
    }



    public Flux<TacoOrder> findOrdersForUser(String username, int pageSize) {
        log.info("🔍 Finding orders for user: {}", username);
        return userRepository.findByUsername(username)
                .flatMapMany(user -> {
                    UserUDT userUDT = toUserUDT(user);
                    return orderRepository.findByUser(userUDT, PageRequest.of(0, pageSize))
                            .doOnNext(order -> log.info("🔍 Found order: {}", order));
                });
    }


    public Mono<TacoOrder> processOrder(String username, TacoOrder order) {
        return findOrCreateOrder(username, order)
                .flatMap(orderToSave -> {
                    orderToSave.placedAt();
                    log.info("▶️▶️▶️ Processing order: {}", orderToSave);
                    return save(orderToSave);
                });
    }

    public Mono<TacoOrder> save(TacoOrder order) {
        return orderRepository.save(order)
                .doOnSuccess(savedOrder -> log.info("🌟🌟🌟 Saved order: {}", savedOrder))
                .doOnError(error -> log.error("❌❌❌ Failed to save order: {}", error.getMessage()));
    }

    // Метод для конвертации User в UserUDT
    private UserUDT toUserUDT(User user) {
        if (user.getAddress() != null) {
            return user.getAddress();  // Возвращаем объект UserUDT напрямую
        }
        // Возвращаем null или создаем новый UserUDT, если нет адреса
        return null;
    }

}
