package pt.galina.in_memory.data;

import pt.galina.in_memory.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
