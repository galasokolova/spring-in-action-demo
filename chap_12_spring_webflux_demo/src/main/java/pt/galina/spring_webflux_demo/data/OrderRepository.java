package pt.galina.spring_webflux_demo.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.entity.user.User;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<TacoOrder, String> {
    Flux<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
