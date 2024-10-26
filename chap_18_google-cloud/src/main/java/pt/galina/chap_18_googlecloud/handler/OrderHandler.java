package pt.galina.chap_18_googlecloud.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_18_googlecloud.entity.taco.TacoOrder;
import pt.galina.chap_18_googlecloud.service.TacoOrderService;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
public class OrderHandler {

    private final TacoOrderService tacoOrderService;
    private final Validator validator;

    @Autowired
    public OrderHandler(TacoOrderService tacoOrderService, Validator validator) {
        this.tacoOrderService = tacoOrderService;
        this.validator = validator;
    }

    public Mono<ServerResponse> showOrderForm(ServerRequest request) {
        return request.session()
                .flatMap(session -> {
                    Mono<Authentication> authMono = ReactiveSecurityContextHolder.getContext()
                            .map(SecurityContext::getAuthentication);

                    return authMono.flatMap(auth -> {
                        String username = auth.getName();
                        TacoOrder order = session.getAttribute("tacoOrder");

                        return tacoOrderService.findOrCreateOrder(username, order)
                                .flatMap(existingOrder -> {
                                    if (order == null) {
                                        session.getAttributes().put("tacoOrder", existingOrder);
                                    }
                                    return ServerResponse.ok()
                                            .contentType(MediaType.TEXT_HTML)
                                            .render("orderForm", Map.of("tacoOrder", existingOrder));
                                });
                    });
                });
    }

    public Mono<ServerResponse> processOrder(ServerRequest request) {
        return request.formData()
                .flatMap(formData -> request.session()
                        .flatMap(session -> {
                            TacoOrder tacoOrder = session.getAttribute("tacoOrder");

                            if (tacoOrder == null) {
                                log.warn("No TacoOrder found in session.");
                                return ServerResponse.badRequest().build();
                            }

                            tacoOrder.setDeliveryName(formData.getFirst("deliveryName"));
                            tacoOrder.setDeliveryStreet(formData.getFirst("deliveryStreet"));
                            tacoOrder.setDeliveryCity(formData.getFirst("deliveryCity"));
                            tacoOrder.setDeliveryState(formData.getFirst("deliveryState"));
                            tacoOrder.setDeliveryZip(formData.getFirst("deliveryZip"));
                            tacoOrder.setCcNumber(formData.getFirst("ccNumber"));
                            tacoOrder.setCcExpiration(formData.getFirst("ccExpiration"));
                            tacoOrder.setCcCVV(formData.getFirst("ccCVV"));

                            BindingResult bindingResult = new BeanPropertyBindingResult(tacoOrder, "tacoOrder");
                            validator.validate(tacoOrder, bindingResult);

                            if (bindingResult.hasErrors()) {
                                return ServerResponse.ok()
                                        .contentType(MediaType.TEXT_HTML)
                                        .render("orderForm", Map.of(
                                                "tacoOrder", tacoOrder,
                                                "org.springframework.validation.BindingResult.tacoOrder", bindingResult));
                            }

                            return request.principal()
                                    .flatMap(principal -> tacoOrderService.processOrder(principal.getName(), tacoOrder))
                                    .flatMap(savedOrder -> ServerResponse.seeOther(URI.create("/orders/orderList")).build());
                        }));
    }

    public Mono<ServerResponse> ordersForUser(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> request.session()
                        .flatMap(session -> tacoOrderService.findOrdersForUser(principal.getName(), 20)
                                .collectList()
                                .flatMap(orders -> {
                                    // Завершаем сессию после отображения списка заказов
                                    return ServerResponse.ok()
                                            .contentType(MediaType.TEXT_HTML)
                                            .render("orderList", Map.of("orders", orders))
                                            .doOnTerminate(session::invalidate);
                                })));
    }

}
