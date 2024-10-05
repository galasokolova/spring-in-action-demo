package pt.galina.chap_17_jmxmonitoring.entity.taco.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_17_jmxmonitoring.entity.taco.TacoOrder;
import pt.galina.chap_17_jmxmonitoring.entity.user.User;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveMongoRepository<TacoOrder, String> {
    Flux<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}

