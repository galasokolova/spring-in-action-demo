package pt.galina.resource_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.galina.resource_server.data.IngredientRepository;
import pt.galina.resource_server.entity.taco.Ingredient;


import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> ingredientById(@PathVariable("id") String id) {
        Optional<Ingredient> optIngredient = ingredientRepository.findById(id);
        return optIngredient.map(
                        ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient saved = ingredientRepository.save(ingredient);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable String id) {
        ingredientRepository.deleteById(id);
    }
}

