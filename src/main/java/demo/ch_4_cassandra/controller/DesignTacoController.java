//package demo.ch_4_cassandra.controller;
//
//import demo.ch_4_cassandra.data.IngredientRepository;
//import demo.ch_4_cassandra.entity.Ingredient;
//import demo.ch_4_cassandra.entity.Ingredient.Type;
//import demo.ch_4_cassandra.entity.Taco;
//import demo.ch_4_cassandra.entity.TacoOrder;
//import demo.ch_4_cassandra.entity.TacoUDT;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/design")
//@SessionAttributes("tacoOrder")
//public class DesignTacoController {
//
//    private final IngredientRepository ingredientRepo;
//
//    @Autowired
//    public DesignTacoController(IngredientRepository ingredientRepo) {
//        this.ingredientRepo = ingredientRepo;
//    }
//
//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredientRepo.findAll().forEach(ingredients::add);
//
//        Type[] types = Ingredient.Type.values();
//        for (Type type : types) {
//            model.addAttribute(type.toString().toLowerCase(),
//                    filterByType(ingredients, type));
//        }
//    }
//
//    @ModelAttribute(name = "tacoOrder")
//    public TacoOrder order() {
//        return new TacoOrder();
//    }
//
//    @ModelAttribute(name = "taco")
//    public Taco taco() {
//        return new Taco();
//    }
//
//    @GetMapping
//    public String showDesignForm() {
//        return "design";
//    }
//
//    @PostMapping
//    public String processTaco(
//            @Valid Taco taco, Errors errors,
//            @ModelAttribute TacoOrder tacoOrder) {
//
//        if (errors.hasErrors()) {
//            return "design";
//        }
//
//        tacoOrder.addTaco(new TacoUDT(taco.getName(), taco.getIngredients()));
//
//        return "redirect:/orders/current";
//    }
//
//    private Iterable<Ingredient> filterByType(
//            List<Ingredient> ingredients, Type type) {
//        return ingredients
//                .stream()
//                .filter(x -> x.getType().equals(type))
//                .toList();
//    }
//
//}