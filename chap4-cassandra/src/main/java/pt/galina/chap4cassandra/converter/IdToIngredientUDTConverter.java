package pt.galina.chap4cassandra.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pt.galina.chap4cassandra.data.IngredientRepository;
import pt.galina.chap4cassandra.entity.IngredientUDT;

@Component
public class IdToIngredientUDTConverter implements Converter<String, IngredientUDT> {

    private final IngredientRepository ingredientRepository;

    public IdToIngredientUDTConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(@NonNull String id) {
        return ingredientRepository.findById(id)
                .map(i -> new IngredientUDT(i.getName(), i.getType()))
                .orElse(null);
    }
}
