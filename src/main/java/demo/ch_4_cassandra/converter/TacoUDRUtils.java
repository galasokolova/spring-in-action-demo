package demo.ch_4_cassandra.converter;

import demo.ch_4_cassandra.entity.Ingredient;
import demo.ch_4_cassandra.entity.IngredientUDT;
import demo.ch_4_cassandra.entity.Taco;
import demo.ch_4_cassandra.entity.TacoUDT;

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
