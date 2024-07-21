package pt.galina.method_level_security.entity.taco.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class IngredientRef {

    @Column(name = "ingredient")
    private String ingredient;

}
