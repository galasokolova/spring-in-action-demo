package pt.galina.kafka_producer.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafka_producer.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
