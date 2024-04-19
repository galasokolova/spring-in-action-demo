package pt.galina.client_app.service;

import pt.galina.client_app.entity.taco.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();

    Ingredient addIngredient(Ingredient ingredient);
}
