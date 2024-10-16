package pt.galina.dockercontainerdemo.entity.taco.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.galina.dockercontainerdemo.entity.taco.Ingredient;

@CrossOrigin(origins="http://localhost:8082")
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
