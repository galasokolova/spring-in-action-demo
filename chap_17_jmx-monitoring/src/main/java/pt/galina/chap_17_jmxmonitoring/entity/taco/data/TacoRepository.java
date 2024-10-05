package pt.galina.chap_17_jmxmonitoring.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_17_jmxmonitoring.entity.taco.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}
