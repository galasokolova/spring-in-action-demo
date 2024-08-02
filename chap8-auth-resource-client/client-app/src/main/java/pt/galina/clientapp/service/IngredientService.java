package pt.galina.clientapp.service;

import pt.galina.clientapp.entity.taco.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();

    Ingredient addIngredient(Ingredient ingredient);
}
