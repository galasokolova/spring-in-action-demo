package pt.galina.spring_data_rest.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import pt.galina.spring_data_rest.entity.taco.Ingredient;

@Component
public class IngredientClient {

    private final RestTemplate rest;
    public IngredientClient(RestTemplate rest) {
        this.rest = rest;
    }

    public Ingredient getIngredientById(String ingredientId) {
        return rest.getForObject("http://localhost:8080/api/ingredients/{id}",
                Ingredient.class,
                ingredientId);
    }

    /*
     public Ingredient getIngredientById(String ingredientId) {
     Map<String, String> urlVariables = new HashMap<>();
     urlVariables.put("id", ingredientId);
     return rest.getForObject("http://localhost:8080/ingredients/{id}",
     Ingredient.class, urlVariables);
    }

    public Ingredient getIngredientById(String ingredientId) {
     Map<String, String> urlVariables = new HashMap<>();
     urlVariables.put("id", ingredientId);
     URI url = UriComponentsBuilder
     .fromHttpUrl("http://localhost:8080/ingredients/{id}")
     .build(urlVariables);
     return rest.getForObject(url, Ingredient.class);
    }

    public Ingredient getIngredientById(String ingredientId) {
     ResponseEntity<Ingredient> responseEntity =
     rest.getForEntity("http://localhost:8080/ingredients/{id}",
     Ingredient.class, ingredientId);
     log.info("Fetched time: {}",
     responseEntity.getHeaders().getDate());
     return responseEntity.getBody();
    }

     */

    public void updateIngredient(Ingredient ingredient) {
        rest.put("http://localhost:8080/api/ingredients/{id}",
                ingredient,
                ingredient.getId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        rest.delete("http://localhost:8080/api/ingredients/{id}",
                ingredient.getId());
    }

    public Ingredient writeIngredients(Ingredient ingredient) {
        return rest.postForObject("http://localhost:8080/api/ingredients",
                ingredient, Ingredient.class);
    }

    /*
    public java.net.URI createIngredient(Ingredient ingredient) {
     return rest.postForLocation("http://localhost:8080/ingredients",
     ingredient);
    }

    public Ingredient createIngredient(Ingredient ingredient) {
     ResponseEntity<Ingredient> responseEntity =
     rest.postForEntity("http://localhost:8080/ingredients",
     ingredient,
     Ingredient.class);
     log.info("New resource created at {}",
     responseEntity.getHeaders().getLocation());
     return responseEntity.getBody();
    }

     */



}
