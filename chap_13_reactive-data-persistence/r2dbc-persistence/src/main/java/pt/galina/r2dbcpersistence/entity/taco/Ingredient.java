package pt.galina.r2dbcpersistence.entity.taco;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table("ingredient")  // Указываем таблицу PostgreSQL
public class Ingredient {
    @Id
    private Long id;  // Поле для идентификатора, будет автоинкрементировано в PostgreSQL

    private @NonNull String slug;  // Используем для уникальной идентификации ингредиента

    private @NonNull String name;  // Название ингредиента

    private @NonNull Type type;  // Тип ингредиента (WRAP, PROTEIN и т.д.)

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}

