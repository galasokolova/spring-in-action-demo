package pt.galina.spring_webflux_demo.entity.taco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Taco {

    @Id
    private String id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @DBRef // refers to other documents in MongoDB
    private List<Ingredient> ingredients = new ArrayList<>();

    // private TacoOrder tacoOrder; // tacoOrder has been removed, as we do not need it in ReactiveMongoRepository

    private Date createdAt;

    public Taco() { // Конструктор по умолчанию для MongoDB
        this.createdAt = new Date();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
