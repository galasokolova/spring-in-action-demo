package pt.galina.chap_5.in_memory_user.data;

import pt.galina.chap_5.in_memory_user.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
