package pt.galina.r2dbcpersistence.entity.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pt.galina.r2dbcpersistence.entity.taco.Taco;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}

