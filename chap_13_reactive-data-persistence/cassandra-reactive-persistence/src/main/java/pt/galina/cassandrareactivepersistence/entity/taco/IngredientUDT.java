package pt.galina.cassandrareactivepersistence.entity.taco;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Setter
@Getter
@UserDefinedType("ingredient")
public class IngredientUDT {

    // Геттеры и сеттеры
    private String name;
    private Ingredient.Type type;

    public IngredientUDT(String name, Ingredient.Type type) {
        this.name = name;
        this.type = type;
    }

}

