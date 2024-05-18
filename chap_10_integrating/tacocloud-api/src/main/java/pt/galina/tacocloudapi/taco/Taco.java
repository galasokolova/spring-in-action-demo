package pt.galina.tacocloudapi.taco;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class Taco {

    private Integer id;
    private String name;
    private List<String> ingredients;

    public Taco(String name) {
        this.name = name;
    }

    public void setIngredientCodes(List<String> ingredientCodes) {
        this.ingredients = ingredientCodes;
    }
}
