package pt.galina.cassandra.converter;

import pt.galina.cassandra.entity.Ingredient;
import pt.galina.cassandra.entity.IngredientUDT;
import pt.galina.cassandra.entity.Taco;
import pt.galina.cassandra.entity.TacoUDT;

import java.util.List;

public class TacoUDRUtils {

    public static TacoUDT toTacoUDT(Taco taco) {
        return new TacoUDT(taco.getName(), taco.getIngredients());
    }

    public static List<IngredientUDT> toIngredientUDTs(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(TacoUDRUtils::toIngredientUDT)
                .toList();
    }

    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }

}
