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

    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public Mono<Void> addIngredientsToModel(Model model) {
        return ingredientRepo.findAll()
                .collectList()
                .doOnNext(ingredients -> {
                    Ingredient.Type[] types = Ingredient.Type.values();
                    for (Ingredient.Type type : types) {
                        List<Ingredient> filtered = filterByType(ingredients, type);
                        model.addAttribute(type.toString().toLowerCase(), filtered);
                    }
                })
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

//    @PostMapping
//    public Mono<String> processTaco(
//            @Valid Taco taco, Errors errors,
//            @ModelAttribute TacoOrder tacoOrder) {
//
//        if (errors.hasErrors()) {
//            return Mono.just("design");
//        }
//
//        tacoOrder.addTaco(taco);
//        log.info("\uD83C\uDF1F Taco saved: {}", tacoOrder);
//        log.info("\uD83C\uDF1F Ingredients: {}", taco.getIngredients());
//        log.info("\uD83C\uDF1F Taco Id: {}", taco.getId());
//
//        return Mono.just("redirect:/orders/current");
//    }

    @PostMapping
    public Mono<String> processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return Mono.just("design");
        }

        // Для каждой выбранной пользователем ингредиента ищем его в репозитории
        return Flux.fromIterable(taco.getIngredientIds())
                .flatMap(ingredientId -> ingredientRepo.findById(ingredientId)
                        .map(ingredient -> {
                            taco.addIngredient(ingredient);
                            return ingredient;
                        })
                )
                .then(Mono.defer(() -> {
                    // Логирование добавленного тако и его ингредиентов
                    tacoOrder.addTaco(taco);
                    log.info("\uD83C\uDF1F Taco saved: {}", tacoOrder);
                    log.info("\uD83C\uDF1F Ingredients: {}", taco.getIngredients());
                    log.info("\uD83C\uDF1F Taco Id: {}", taco.getId());

                    return Mono.just("redirect:/orders/current");
                }));
    }




    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
