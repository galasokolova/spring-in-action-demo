package pt.galina.chap_18_googlecloud.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_18_googlecloud.entity.taco.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}
