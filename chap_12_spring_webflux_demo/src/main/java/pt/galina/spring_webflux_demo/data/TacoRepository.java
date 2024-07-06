package pt.galina.spring_webflux_demo.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Flux;

public interface TacoRepository extends ReactiveMongoRepository<Taco, String> {
    Flux<Taco> findAllByOrderByCreatedAtDesc(); // Метод для получения тако в порядке убывания даты создания
}

