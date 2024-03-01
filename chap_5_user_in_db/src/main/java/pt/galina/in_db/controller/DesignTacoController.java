package pt.galina.in_db.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import pt.galina.in_db.data.IngredientRepository;
import pt.galina.in_db.data.TacoRepository;
import pt.galina.in_db.entity.taco.Ingredient;
import pt.galina.in_db.entity.taco.Ingredient.Type;
import pt.galina.in_db.entity.taco.Taco;
import pt.galina.in_db.entity.taco.TacoOrder;
import pt.galina.in_db.entity.user.User;
import pt.galina.in_db.entity.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;

    private TacoRepository tacoRepo;

    private UserRepository userRepo;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository tacoRepo,
            UserRepository userRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
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
    public String showDesignForm(Model model) {
//        model.addAttribute("tacoOrder", new TacoOrder());

        return "design";
    }

    @PostMapping
    public String processTaco(SessionStatus sessionStatus,
            @Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder order, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "design";
        }

        // adding Taco to TacoOrder
        order.addTaco(taco);

        // persisting TacoOrder in session
        model.addAttribute("order", order);



        return "redirect:/orders/current";
    }
    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
