package pt.galina.chap4cassandra.data;


import org.springframework.data.repository.CrudRepository;
import pt.galina.chap4cassandra.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
