package pt.galina.tacocloudapi.taco;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Taco {

    private Integer id;
    private String name;
    private List<String> ingredients = new ArrayList<>(); // Initialize to avoid null

    public Taco(String name) {
        this.name = name;
    }

    public void setIngredientCodes(List<String> ingredientCodes) {
        this.ingredients = ingredientCodes;
    }

    public List<String> getIngredientCodes() {
        return ingredients;
    }
}
