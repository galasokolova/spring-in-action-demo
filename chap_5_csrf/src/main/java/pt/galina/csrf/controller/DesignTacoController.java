package pt.galina.csrf.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pt.galina.csrf.data.IngredientRepository;
import pt.galina.csrf.entity.taco.Ingredient;
import pt.galina.csrf.entity.taco.Ingredient.Type;
import pt.galina.csrf.entity.taco.Taco;
import pt.galina.csrf.entity.taco.TacoOrder;
import pt.galina.csrf.entity.user.User;
import pt.galina.csrf.entity.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;

    private final UserRepository userRepo;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.userRepo = userRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = StreamSupport
                .stream(ingredientRepo.findAll().spliterator(), false)
                .toList();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        return userRepo.findByUsername(username);
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder order, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "design";
        }
        order.addTaco(taco);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
