package pt.galina.chap4cassandra.converter;

import org.junit.jupiter.api.Test;
import pt.galina.chap4cassandra.entity.Ingredient;
import pt.galina.chap4cassandra.entity.IngredientUDT;
import pt.galina.chap4cassandra.entity.Taco;
import pt.galina.chap4cassandra.entity.TacoUDT;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TacoUDTUtilsTest {

    @Test
    void toTacoUDT_ShouldConvertTacoToTacoUDT() {
        // Arrange
        IngredientUDT ingredient1 = new IngredientUDT("Flour Tortilla", Ingredient.Type.WRAP);
        IngredientUDT ingredient2 = new IngredientUDT("Ground Beef", Ingredient.Type.PROTEIN);
        List<IngredientUDT> ingredients = List.of(ingredient1, ingredient2);

        Taco taco = new Taco();
        taco.setName("Test Taco");
        taco.setIngredients(ingredients);

        // Act
        TacoUDT tacoUDT = TacoUDTUtils.toTacoUDT(taco);

        // Assert
        assertEquals("Test Taco", tacoUDT.getName());
        assertEquals(2, tacoUDT.getIngredients().size());
        assertEquals("Flour Tortilla", tacoUDT.getIngredients().get(0).getName());
        assertEquals(Ingredient.Type.WRAP, tacoUDT.getIngredients().get(0).getType());
        assertEquals("Ground Beef", tacoUDT.getIngredients().get(1).getName());
        assertEquals(Ingredient.Type.PROTEIN, tacoUDT.getIngredients().get(1).getType());
    }

    @Test
    void toIngredientUDTs_ShouldConvertListOfIngredientsToListOfIngredientUDTs() {
        // Arrange
        Ingredient ingredient1 = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
        Ingredient ingredient2 = new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
        List<Ingredient> ingredients = List.of(ingredient1, ingredient2);

        // Act
        List<IngredientUDT> ingredientUDTs = TacoUDTUtils.toIngredientUDTs(ingredients);

        // Assert
        assertEquals(2, ingredientUDTs.size());
        assertEquals("Flour Tortilla", ingredientUDTs.get(0).getName());
        assertEquals(Ingredient.Type.WRAP, ingredientUDTs.get(0).getType());
        assertEquals("Ground Beef", ingredientUDTs.get(1).getName());
        assertEquals(Ingredient.Type.PROTEIN, ingredientUDTs.get(1).getType());
    }

    @Test
    void toIngredientUDT_ShouldConvertIngredientToIngredientUDT() {
        // Arrange
        Ingredient ingredient = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);

        // Act
        IngredientUDT ingredientUDT = TacoUDTUtils.toIngredientUDT(ingredient);

        // Assert
        assertEquals("Flour Tortilla", ingredientUDT.getName());
        assertEquals(Ingredient.Type.WRAP, ingredientUDT.getType());
    }
}
