package pt.galina.clientreactive.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.clientreactive.entity.taco.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}