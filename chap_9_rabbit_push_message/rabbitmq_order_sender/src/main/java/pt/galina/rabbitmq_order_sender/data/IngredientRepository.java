package pt.galina.rabbitmq_order_sender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq_order_sender.entity.taco.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
