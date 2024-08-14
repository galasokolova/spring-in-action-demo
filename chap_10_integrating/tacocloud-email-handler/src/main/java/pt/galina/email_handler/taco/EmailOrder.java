package pt.galina.email_handler.taco;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmailOrder {

    private final String email;
    private List<Taco> tacos = new ArrayList<>();

    @JsonCreator
    public EmailOrder(@JsonProperty("email") String email) {
        this.email = email;
    }

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

    public Taco findTacoByName(String tacoName) {
        if (tacos != null) {
            for (Taco taco : tacos) {
                if (taco != null && taco.getName().equalsIgnoreCase(tacoName)) {
                    return taco;
                }
            }
        }
        return null;
    }
}