package pt.galina.spring_webflux_demo.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientRepository extends ReactiveMongoRepository<Ingredient, String> {
    Mono<Ingredient> findById(String id);
}
