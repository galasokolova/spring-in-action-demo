package pt.galina.cassandrareactivepersistence.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import pt.galina.cassandrareactivepersistence.entity.taco.IngredientUDT;
import pt.galina.cassandrareactivepersistence.entity.taco.data.IngredientRepository;
import reactor.core.publisher.Mono;

@Component
public class IdToIngredientUDTConverter implements Converter<String, Mono<IngredientUDT>> {

    private final IngredientRepository ingredientRepository;

    public IdToIngredientUDTConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Mono<IngredientUDT> convert(@NonNull String id) {
        return ingredientRepository.findById(id)
                .map(i -> new IngredientUDT(i.getName(), i.getType()));
    }
}

