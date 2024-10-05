package pt.galina.chap_17_jmxmonitoring.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pt.galina.chap_17_jmxmonitoring.entity.taco.Ingredient;
import pt.galina.chap_17_jmxmonitoring.service.IngredientService;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final IngredientService ingredientService;

    public IngredientByIdConverter(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientService.findById(id).block();
    }
}