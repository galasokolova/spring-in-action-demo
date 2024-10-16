package pt.galina.chap_18_googlecloud.entity.taco.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_18_googlecloud.entity.taco.TacoOrder;
import pt.galina.chap_18_googlecloud.entity.user.User;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<TacoOrder, String> {
    Flux<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}

