package pt.galina.cassandrareactivepersistence.entity.taco.data;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.domain.Pageable;
import pt.galina.cassandrareactivepersistence.entity.taco.TacoOrder;
import pt.galina.cassandrareactivepersistence.entity.user.UserUDT;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface OrderRepository extends ReactiveCassandraRepository<TacoOrder, UUID> {

    Flux<TacoOrder> findByUser(UserUDT user, Pageable pageable);
}

