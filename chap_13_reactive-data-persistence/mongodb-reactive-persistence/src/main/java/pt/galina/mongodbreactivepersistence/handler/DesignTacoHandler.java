package pt.galina.mongodbreactivepersistence.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import pt.galina.mongodbreactivepersistence.entity.taco.Ingredient;
import pt.galina.mongodbreactivepersistence.entity.taco.Taco;
import pt.galina.mongodbreactivepersistence.entity.taco.TacoOrder;
import pt.galina.mongodbreactivepersistence.service.IngredientService;
import pt.galina.mongodbreactivepersistence.service.TacoOrderService;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DesignTacoHandler {

    private final IngredientService ingredientService;
    private final TacoOrderService tacoOrderService;

    @Autowired
    public DesignTacoHandler(IngredientService ingredientService, TacoOrderService tacoOrderService) {
        this.ingredientService = ingredientService;
        this.tacoOrderService = tacoOrderService;
    }

    public Mono<ServerResponse> showDesignForm(ServerRequest request) {
        return ingredientService.findAll()
                .collectList()
                .flatMap(this::createModelForDesign)
                .flatMap(model -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model));
    }


    public Mono<ServerResponse> processTaco(ServerRequest request) {
        return request.formData()
                .flatMap(formData -> {
                    Taco taco = new Taco();
                    taco.setName(formData.getFirst("name"));

                    List<String> ingredientIds = formData.get("ingredients");

                    // Validation of ingredients
                    BindingResult bindingResult = getBindingResult(taco, ingredientIds);

                    Mono<ServerResponse> ingredientService1 = getErrorForm(bindingResult, taco);

                    if (ingredientService1 != null) return ingredientService1;

                    // If there are no errors, continue creating the order
                    return ingredientService.findAllById(ingredientIds)
                            .collectList()
                            .flatMap(ingredients -> {
                                taco.setIngredients(ingredients);
                                return addToOrder(taco, request);
                            })
                            .flatMap(order -> ServerResponse
                                    .seeOther(URI.create("/orders/current"))
                                    .build());
                });
    }

    private Mono<ServerResponse> getErrorForm(BindingResult bindingResult, Taco taco) {
        if (bindingResult.hasErrors()) {
            // If there are errors, return the error form
            return ingredientService.findAll()
                    .collectList()
                    .flatMap(ingredients -> {
                        Map<String, Object> model = new HashMap<>();
                        model.put("taco", taco);
                        model.put("wrap", ingredients.stream().filter(ing -> ing.getType() == Ingredient.Type.WRAP).collect(Collectors.toList()));
                        model.put("protein", ingredients.stream().filter(ing -> ing.getType() == Ingredient.Type.PROTEIN).collect(Collectors.toList()));
                        model.put("cheese", ingredients.stream().filter(ing -> ing.getType() == Ingredient.Type.CHEESE).collect(Collectors.toList()));
                        model.put("veggies", ingredients.stream().filter(ing -> ing.getType() == Ingredient.Type.VEGGIES).collect(Collectors.toList()));
                        model.put("sauce", ingredients.stream().filter(ing -> ing.getType() == Ingredient.Type.SAUCE).collect(Collectors.toList()));
                        model.put("org.springframework.validation.BindingResult.taco", bindingResult);
                        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model);
                    });
        }
        return null;
    }

    private static BindingResult getBindingResult(Taco taco, List<String> ingredientIds) {
        BindingResult bindingResult = new BeanPropertyBindingResult(taco, "taco");

        // If the name is empty or less than 5 characters
        if (taco.getName() == null || taco.getName().length() < 5) {
            bindingResult.rejectValue("name", "name", "Name must be at least 5 characters long");
        }

        // If there are no ingredients
        if (ingredientIds == null || ingredientIds.isEmpty()) {
            bindingResult.rejectValue("ingredients", "ingredients", "You must choose at least 1 ingredient");
        }
        return bindingResult;
    }


    private Mono<Map<String, Object>> createModelForDesign(List<Ingredient> ingredients) {
        Map<String, Object> model = new HashMap<>();
        model.put("taco", new Taco());

        Map<Ingredient.Type, List<Ingredient>> ingredientsByType = ingredients.stream()
                .collect(Collectors.groupingBy(Ingredient::getType));

        ingredientsByType.forEach((type, ingredientList) ->
                model.put(type.toString().toLowerCase(), ingredientList)
        );

        return Mono.just(model);
    }

    private Mono<TacoOrder> addToOrder(Taco taco, ServerRequest request) {
        return request.session()
                .flatMap(session -> {
                    TacoOrder order = session.getAttribute("tacoOrder");
                    if (order == null) {
                        order = new TacoOrder();
                        session.getAttributes().put("tacoOrder", order);
                    }
                    order.addTaco(taco);
                    log.info("🌀🌀🌀 Added taco to order: {}", order);
                    return tacoOrderService.save(order).doOnSuccess(savedOrder -> {
                        session.getAttributes().put("tacoOrder", savedOrder);
                        log.info("🌀🌀🌀 Updated session with saved order: {}", savedOrder);
                    }).thenReturn(order);
                });
    }
}
