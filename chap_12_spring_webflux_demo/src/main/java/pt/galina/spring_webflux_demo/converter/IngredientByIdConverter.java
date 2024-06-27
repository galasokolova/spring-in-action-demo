package pt.galina.spring_webflux_demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepo;

    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(@NonNull String id) {
        return ingredientRepo.findById(id).block(); // Преобразование Mono в Ingredient с использованием .block()
    }
}
