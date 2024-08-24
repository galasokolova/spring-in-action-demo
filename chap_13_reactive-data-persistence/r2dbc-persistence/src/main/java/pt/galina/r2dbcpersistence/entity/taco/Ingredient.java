package pt.galina.r2dbcpersistence.entity.taco;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Ingredient {
    @Id
    private Long id;
    private @NonNull String slug; //identification
    private @NonNull String name;  // R2DBC requires getters and setters, that´s why the fields are not final
    private @NonNull Type type; // @NonNull is used in order to allow Lombok create constructors. They are generated with @RequiredArgsConstructor

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
