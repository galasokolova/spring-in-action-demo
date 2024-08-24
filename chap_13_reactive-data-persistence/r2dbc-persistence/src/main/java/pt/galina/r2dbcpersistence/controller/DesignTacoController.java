package pt.galina.r2dbcpersistence.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import pt.galina.r2dbcpersistence.entity.taco.Ingredient;
import pt.galina.r2dbcpersistence.entity.taco.Taco;
import pt.galina.r2dbcpersistence.entity.taco.TacoOrder;
import pt.galina.r2dbcpersistence.entity.taco.data.IngredientRepository;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @PostMapping
    public Mono<String> processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return Mono.just("design");
        }

        tacoOrder.addTaco(taco);

        return Mono.just("redirect:/orders/current");
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
