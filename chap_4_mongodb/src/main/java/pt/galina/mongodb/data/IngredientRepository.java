package pt.galina.mongodb.data;


import pt.galina.mongodb.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
