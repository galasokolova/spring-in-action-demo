package pt.galina.chap_5_user_in_db.in_db.entity.taco;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class IngredientRef {

    @Column(name = "ingredient")
    private String ingredient;

}
