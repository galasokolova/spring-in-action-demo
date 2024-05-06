package pt.galina.email_handler.taco;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
public class Ingredient {
    @Id
    private String id;
    private String name;
}