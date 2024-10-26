package pt.galina.chap_18_googlecloud.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_18_googlecloud.entity.taco.Taco;
import pt.galina.chap_18_googlecloud.entity.taco.TacoOrder;
import pt.galina.chap_18_googlecloud.service.IngredientService;
import pt.galina.chap_18_googlecloud.service.TacoOrderService;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

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
        return tacoOrderService.createModelForDesign()
                .flatMap(model -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model));
    }

    public Mono<ServerResponse> processTaco(ServerRequest request) {
        return request.formData()
                .flatMap(formData -> {
                    Taco taco = new Taco();
                    taco.setName(formData.getFirst("name"));
                    List<String> ingredientIds = formData.get("ingredients");

                    BindingResult bindingResult = getBindingResult(taco, ingredientIds);
                    Mono<ServerResponse> errorResponse = getErrorForm(bindingResult, taco);

                    if (errorResponse != null) return errorResponse;

                    return ingredientService.findAllById(ingredientIds)
                            .collectList()
                            .flatMap(ingredients -> {
                                taco.setIngredients(ingredients);
                                return addToOrder(taco, request);
                            })
                            .flatMap(order -> ServerResponse.seeOther(URI.create("/orders/current")).build());
                });
    }

    private Mono<ServerResponse> getErrorForm(BindingResult bindingResult, Taco taco) {
        if (bindingResult.hasErrors()) {
            return tacoOrderService.createModelForDesign()
                    .map(model -> {
                        model.put("taco", taco);
                        model.put("org.springframework.validation.BindingResult.taco", bindingResult);
                        return model;
                    })
                    .flatMap(model -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model));
        }
        return null;
    }

    private BindingResult getBindingResult(Taco taco, List<String> ingredientIds) {
        BindingResult bindingResult = new BeanPropertyBindingResult(taco, "taco");

        if (taco.getName() == null || taco.getName().length() < 5) {
            bindingResult.rejectValue("name", "name", "Name must be at least 5 characters long");
        }
        if (ingredientIds == null || ingredientIds.isEmpty()) {
            bindingResult.rejectValue("ingredients", "ingredients", "You must choose at least 1 ingredient");
        }
        return bindingResult;
    }

    private Mono<TacoOrder> addToOrder(Taco taco, ServerRequest request) {
        return request.session()
                .flatMap(session -> {
                    TacoOrder order = session.getAttribute("tacoOrder");
                    if (order == null || tacoOrderService.isCompleted(order)) {  // Проверка завершенности заказа
                        order = new TacoOrder();
                        session.getAttributes().put("tacoOrder", order);
                    }
                    order.addTaco(taco);
                    log.info("🌀 Added taco to order: {}", order);
                    return tacoOrderService.save(order)
                            .doOnSuccess(savedOrder -> session.getAttributes().put("tacoOrder", savedOrder))
                            .thenReturn(order);
                });
    }
}
