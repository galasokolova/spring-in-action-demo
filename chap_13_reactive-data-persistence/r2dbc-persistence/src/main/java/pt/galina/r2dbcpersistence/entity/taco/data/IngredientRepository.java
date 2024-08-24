package pt.galina.r2dbcpersistence.entity.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pt.galina.r2dbcpersistence.entity.taco.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, Long> {
    Mono<Ingredient> findBySlug(String slug);
}

