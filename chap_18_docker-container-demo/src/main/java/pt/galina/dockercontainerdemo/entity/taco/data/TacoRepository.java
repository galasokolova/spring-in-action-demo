package pt.galina.dockercontainerdemo.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.dockercontainerdemo.entity.taco.Taco;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
}
