package pt.galina.chap3jpa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "Ingredient")
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
public class Ingredient {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;


    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
