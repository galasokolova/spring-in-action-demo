package pt.galina.email_handler.taco;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class EmailOrder {

    private final String email;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this. tacos.add(taco);
    }

    public Taco findTacoByName(String tacoName) {
        if (tacos != null) {  // Проверка на null
            for (Taco taco : tacos) {
                if (taco != null && taco.getName().equalsIgnoreCase(tacoName)) {
                    return taco;
                }
            }
        }
        return null;
    }
}