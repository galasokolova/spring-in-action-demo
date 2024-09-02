package pt.galina.cassandrareactivepersistence.entity.taco.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import pt.galina.cassandrareactivepersistence.entity.taco.Ingredient;

public interface IngredientRepository extends ReactiveCassandraRepository<Ingredient, String> {
}
