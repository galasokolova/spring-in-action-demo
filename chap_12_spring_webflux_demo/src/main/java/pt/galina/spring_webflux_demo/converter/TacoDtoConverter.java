package pt.galina.spring_webflux_demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.dto.TacoDTO;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class TacoDtoConverter {
    private final IngredientDtoConverter ingredientDtoConverter;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public TacoDtoConverter(IngredientDtoConverter ingredientDtoConverter, IngredientRepository ingredientRepository) {
        this.ingredientDtoConverter = ingredientDtoConverter;
        this.ingredientRepository = ingredientRepository;
    }

    public TacoDTO toDto(Taco taco) {
        TacoDTO dto = new TacoDTO();
        dto.setId(taco.getId());
        dto.setName(taco.getName());
        dto.setIngredients(taco.getIngredients().stream()
                .map(Ingredient::getId) // Используем только идентификаторы ингредиентов
                .collect(Collectors.toList()));

        return dto;
    }

    public Mono<Taco> toEntity(TacoDTO dto) {
        Taco taco = new Taco();
        taco.setId(dto.getId());
        taco.setName(dto.getName());

        return Flux.fromIterable(dto.getIngredients())
                .flatMap(ingredientRepository::findById)
                .collectList()
                .map(ingredients -> {
                    taco.setIngredients(ingredients);
                    return taco;
                });
    }
}
