package pt.galina.jpa.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class IngredientRef {

    @Column(name = "ingredient")
    private String ingredient;

}
