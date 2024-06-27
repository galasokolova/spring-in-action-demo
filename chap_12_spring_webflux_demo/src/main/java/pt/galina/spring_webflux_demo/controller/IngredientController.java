package pt.galina.spring_webflux_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Ingredient>> ingredientById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Mono<ResponseEntity<Ingredient>> addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient)
                .map(savedIngredient -> new ResponseEntity<>(savedIngredient, HttpStatus.CREATED));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteIngredient(@PathVariable String id) {
        return ingredientRepository.deleteById(id);
    }
}
