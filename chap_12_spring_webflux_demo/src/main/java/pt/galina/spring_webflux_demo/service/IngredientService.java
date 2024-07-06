package pt.galina.spring_webflux_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Mono<Ingredient> save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Flux<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Mono<Ingredient> findById(String id) {
        return ingredientRepository.findById(id);
    }

    public Mono<Void> deleteById(String id) {
        return ingredientRepository.deleteById(id);
    }

    public Flux<Ingredient> findAllById(List<String> ids) {
        return ingredientRepository.findAllById(ids);
    }
}
