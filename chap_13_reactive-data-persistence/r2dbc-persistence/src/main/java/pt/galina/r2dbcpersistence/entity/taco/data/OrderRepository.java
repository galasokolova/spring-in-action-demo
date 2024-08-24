package pt.galina.r2dbcpersistence.entity.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {
    Flux<TacoOrder> findByUserIdOrderByPlacedAtDesc(Long userId, Pageable pageable);
}

