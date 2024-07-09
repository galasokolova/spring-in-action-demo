//package pt.galina.spring_webflux_demo.handler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
//import pt.galina.spring_webflux_demo.entity.taco.Taco;
//import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
//import pt.galina.spring_webflux_demo.service.IngredientService;
//import pt.galina.spring_webflux_demo.service.TacoOrderService;
//import pt.galina.spring_webflux_demo.service.TacoService;
//import reactor.core.publisher.Mono;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class DesignTacoHandler {
//
//    private final TacoService tacoService;
//    private final IngredientService ingredientService;
//    private final TacoOrderService tacoOrderService;
//
//    @Autowired
//    public DesignTacoHandler(TacoService tacoService, IngredientService ingredientService, TacoOrderService tacoOrderService) {
//        this.tacoService = tacoService;
//        this.ingredientService = ingredientService;
//        this.tacoOrderService = tacoOrderService;
//    }
//
//    // Метод для отображения формы дизайна тако
//    public Mono<ServerResponse> showDesignForm(ServerRequest request) {
//        return ingredientService.findAll()
//                .collectList()
//                .flatMap(this::createModelForDesign)
//                .flatMap(model -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model));
//    }
//
//    // Метод для обработки сабмита тако
//    public Mono<ServerResponse> processTaco(ServerRequest request) {
//        return request.formData()
//                .flatMap(formData -> {
//                    Taco taco = new Taco();
//                    taco.setName(formData.getFirst("name"));
//
//                    // Извлечение ингредиентов по их идентификаторам
//                    List<String> ingredientIds = formData.get("ingredients");
//                    return ingredientService.findAllById(ingredientIds)
//                            .collectList()
//                            .flatMap(ingredients -> {
//                                taco.setIngredients(ingredients);
//                                return tacoService.save(taco);
//                            })
//                            .flatMap(savedTaco -> addToOrder(savedTaco, request))
//                            .flatMap(order -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("orderForm", Map.of("tacoOrder", order)));
//                });
//    }
//
//    // Метод для создания модели для формы дизайна тако
//    private Mono<Map<String, Object>> createModelForDesign(List<Ingredient> ingredients) {
//        Map<String, Object> model = new HashMap<>();
//        model.put("taco", new Taco());
//
//        Map<Ingredient.Type, List<Ingredient>> ingredientsByType = ingredients.stream()
//                .collect(Collectors.groupingBy(Ingredient::getType));
//
//        ingredientsByType.forEach((type, ingredientList) ->
//                model.put(type.toString().toLowerCase(), ingredientList)
//        );
//
//        return Mono.just(model);
//    }
//
//    // Метод для добавления тако в заказ
//    private Mono<TacoOrder> addToOrder(Taco taco, ServerRequest request) {
//        return request.session()
//                .flatMap(session -> {
//                    TacoOrder order = session.getAttribute("tacoOrder");
//                    if (order == null) {
//                        order = new TacoOrder();
//                        session.getAttributes().put("tacoOrder", order);
//                    }
//                    order.addTaco(taco);
//                    return tacoOrderService.save(order).thenReturn(order);
//                });
//    }
//}

package pt.galina.spring_webflux_demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.service.IngredientService;
import pt.galina.spring_webflux_demo.service.TacoOrderService;
import pt.galina.spring_webflux_demo.service.TacoService;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DesignTacoHandler {

    private final TacoService tacoService;
    private final IngredientService ingredientService;
    private final TacoOrderService tacoOrderService;

    @Autowired
    public DesignTacoHandler(TacoService tacoService, IngredientService ingredientService, TacoOrderService tacoOrderService) {
        this.tacoService = tacoService;
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
                    return ingredientService.findAllById(ingredientIds)
                            .collectList()
                            .flatMap(ingredients -> {
                                taco.setIngredients(ingredients);
                                return tacoService.save(taco); // Сохранение тако
                            })
                            .flatMap(savedTaco -> addToOrder(savedTaco, request))
                            .flatMap(order -> {
                                return ServerResponse
                                        .seeOther(URI.create("/orders/current"))
                                        .build();
                            });
                });
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
