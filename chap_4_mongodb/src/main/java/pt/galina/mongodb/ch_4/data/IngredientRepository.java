package pt.galina.mongodb.ch_4.data;


import pt.galina.mongodb.ch_4.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
