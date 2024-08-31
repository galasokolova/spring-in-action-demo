package pt.galina.mongodbreactivepersistence.entity.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.galina.mongodbreactivepersistence.entity.taco.Ingredient;

@CrossOrigin(origins="http:/ /localhost:8080")
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}

