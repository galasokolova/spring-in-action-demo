package pt.galina.cassandrareactivepersistence.entity.taco.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import pt.galina.cassandrareactivepersistence.entity.taco.Taco;
import reactor.core.publisher.Flux;

public interface TacoRepository extends ReactiveCassandraRepository<Taco, String> {
    Flux<Taco> findAllByOrderByCreatedAtDesc();
}

