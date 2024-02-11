package demo.ch_4_mongodb.data;


import demo.ch_4_mongodb.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
