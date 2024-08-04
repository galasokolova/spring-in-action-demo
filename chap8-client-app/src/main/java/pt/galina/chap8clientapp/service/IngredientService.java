package pt.galina.chap8clientapp.service;

import pt.galina.chap8clientapp.entity.taco.Ingredient;

public interface IngredientService {
    Iterable<Ingredient> findAll();

    Ingredient addIngredient(Ingredient ingredient);
}
