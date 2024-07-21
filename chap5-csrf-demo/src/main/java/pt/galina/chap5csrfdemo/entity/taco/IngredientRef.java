package pt.galina.chap5csrfdemo.entity.taco;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class IngredientRef {

    @Column(name = "ingredient")
    private String ingredient;

}
