package pt.galina.spring_webflux_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.galina.spring_webflux_demo.data.TacoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TacoService {
    private final TacoRepository tacoRepository;
    private final IngredientService ingredientService;


    @Autowired
    public TacoService(TacoRepository tacoRepository, IngredientService ingredientService) {
        this.tacoRepository = tacoRepository;
        this.ingredientService = ingredientService;
    }

    public Mono<Taco> save(Taco taco) {
        return tacoRepository.save(taco);
    }

    public Flux<Taco> findAll() {
        return tacoRepository.findAll();
    }

    public Mono<Taco> findById(String id) {
        return tacoRepository.findById(id);
    }

    public Mono<Void> deleteById(String id) {
        return tacoRepository.deleteById(id);
    }

    public Mono<Taco> addIngredient(String tacoId, String ingredientId) {
        return tacoRepository.findById(tacoId)
                .flatMap(taco -> retrieveAndAddIngredient(taco, ingredientId));
    }

    private Mono<Taco> retrieveAndAddIngredient(Taco taco, String ingredientId) {
        return ingredientService.findById(ingredientId)
                .flatMap(ingredient -> addIngredientToTacoAndSave(taco, ingredient));
    }

    private Mono<Taco> addIngredientToTacoAndSave(Taco taco, Ingredient ingredient) {
        taco.addIngredient(ingredient);
        return tacoRepository.save(taco);
    }

}

