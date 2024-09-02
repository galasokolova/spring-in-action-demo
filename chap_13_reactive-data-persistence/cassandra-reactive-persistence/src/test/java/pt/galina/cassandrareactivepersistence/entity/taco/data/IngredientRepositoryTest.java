package pt.galina.cassandrareactivepersistence.entity.taco.data;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;
import pt.galina.cassandrareactivepersistence.config.TestConfig;
import pt.galina.cassandrareactivepersistence.entity.taco.Ingredient;
import pt.galina.cassandrareactivepersistence.entity.taco.Ingredient.Type;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataCassandraTest
@Import(TestConfig.class)
public class IngredientRepositoryTest {
    @Autowired
    IngredientRepository ingredientRepo;
    @BeforeEach
    public void setup() {
        Flux<Ingredient> deleteAndInsert = ingredientRepo.deleteAll()
                .thenMany(ingredientRepo.saveAll(
                        Flux.just(
                                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                                new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE)
                        )));
        StepVerifier.create(deleteAndInsert)
                .expectNextCount(3)
                .verifyComplete();
    }
    @Test
    public void shouldSaveAndFetchIngredients() {
        // Проверка, что все ингредиенты сохранены и могут быть извлечены
        StepVerifier.create(ingredientRepo.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(ingredients -> {
                    assertThat(ingredients).hasSize(3);
                    assertThat(ingredients).containsExactlyInAnyOrder(
                            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                            new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE)
                    );
                })
                .verifyComplete();

        // Проверка по ID для конкретного ингредиента
        StepVerifier.create(ingredientRepo.findById("FLTO"))
                .assertNext(ingredient ->
                        assertThat(ingredient).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP))
                )
                .verifyComplete();
    }

}
