package pt.galina.r2dbcpersistence.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import pt.galina.r2dbcpersistence.entity.taco.Ingredient;
import pt.galina.r2dbcpersistence.entity.taco.Taco;
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import pt.galina.r2dbcpersistence.entity.taco.data.IngredientRepository;
import pt.galina.r2dbcpersistence.entity.taco.data.TacoRepository;
import pt.galina.r2dbcpersistence.entity.taco.web.TacoOrderAggregateService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;

    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

    @ModelAttribute
    public Mono<Void> addIngredientsToModel(Model model) {
        return ingredientRepo.findAll()
                .collectList()
                .doOnNext(ingredients -> {
                    log.info("\uD83C\uDF1F Fetched ingredients: {}", ingredients);
                    Ingredient.Type[] types = Ingredient.Type.values();
                    for (Ingredient.Type type : types) {
                        List<Ingredient> filtered = filterByType(ingredients, type);
                        log.info("\uD83C\uDF1F Adding ingredients of type {} to model: {}", type, filtered);
                        model.addAttribute(type.toString().toLowerCase(), filtered);
                    }
                })
                .doOnError(error -> log.error("\uD83C\uDF1F Error fetching ingredients", error))
                .then();
    }


    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        Taco taco = new Taco();
        model.addAttribute("taco", taco);
        return "design";
    }

    @PostMapping
    public Mono<String> processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return Mono.just("design");
        }
        return Flux.fromIterable(taco.getIngredientIds())
                .flatMap(ingredientRepo::findById)
                .collectList()
                .flatMap(ingredients -> {
                    taco.setIngredients(ingredients);
                    log.info("Taco with ingredients: {}", taco);

                    return tacoRepo.save(taco);
                })
                .doOnNext(savedTaco -> {
                    tacoOrder.addTacoId(savedTaco.getId());
                    log.info("Taco saved with ID: {} added to order: {}", savedTaco.getId(), tacoOrder);
                    log.info("Taco saved: {}", savedTaco);
                })
                .then(Mono.just("redirect:/orders/current"));
    }




    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
