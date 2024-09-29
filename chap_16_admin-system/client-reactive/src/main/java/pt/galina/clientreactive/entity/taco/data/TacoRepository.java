package pt.galina.chap_15_actuatordemo.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_15_actuatordemo.entity.taco.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}
