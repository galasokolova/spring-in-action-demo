package pt.galina.chap_18_googlecloud.entity.taco.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.galina.chap_18_googlecloud.entity.taco.Ingredient;

@CrossOrigin(origins="http://localhost:8082")
public interface IngredientRepository extends ReactiveMongoRepository<Ingredient, String> {
}

