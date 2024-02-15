package pt.galina.mongodb.ch_4.converter;

import pt.galina.mongodb.ch_4.data.IngredientRepository;
import pt.galina.mongodb.ch_4.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return ingredientRepo.findById(id).orElse(null);
    }

}