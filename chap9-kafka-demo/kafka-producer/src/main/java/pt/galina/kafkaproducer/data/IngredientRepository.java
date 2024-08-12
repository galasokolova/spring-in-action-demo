package pt.galina.kafkaproducer.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafkaproducer.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
