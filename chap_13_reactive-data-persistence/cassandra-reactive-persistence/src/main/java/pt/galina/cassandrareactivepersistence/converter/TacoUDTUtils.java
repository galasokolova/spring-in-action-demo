package pt.galina.cassandrareactivepersistence.converter;

import pt.galina.cassandrareactivepersistence.entity.taco.Ingredient;
import pt.galina.cassandrareactivepersistence.entity.taco.IngredientUDT;
import pt.galina.cassandrareactivepersistence.entity.taco.Taco;
import pt.galina.cassandrareactivepersistence.entity.taco.TacoUDT;

import java.util.List;

public class TacoUDTUtils {

    public static TacoUDT toTacoUDT(Taco taco) {
        return new TacoUDT(taco.getName(), taco.getIngredients());
    }

    public static List<IngredientUDT> toIngredientUDTs(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(TacoUDTUtils::toIngredientUDT)
                .toList();
    }

    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }

}
