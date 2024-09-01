package pt.galina.spring_webflux_demo.handler;

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
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.service.TacoOrderService;
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
                    Mono<Authentication> authenticationMono = ReactiveSecurityContextHolder.getContext()
                            .map(SecurityContext::getAuthentication);

                    return authenticationMono.flatMap(authentication -> {
                        String username = authentication.getName();
                        TacoOrder existingOrder = session.getAttribute("tacoOrder");

                        return tacoOrderService.findOrCreateOrder(username, existingOrder)
                                .flatMap(order -> {
                                    if (existingOrder == null) {
                                        session.getAttributes().put("tacoOrder", order);
                                    }
                                    return ServerResponse
                                            .ok()
                                            .contentType(MediaType.TEXT_HTML)
                                            .render("orderForm", Map.of("tacoOrder", order));
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

                            tacoOrder.setCcNumber(formData.getFirst("ccNumber"));
                            tacoOrder.setCcExpiration(formData.getFirst("ccExpiration"));
                            tacoOrder.setCcCVV(formData.getFirst("ccCVV"));

                            log.info("TacoOrder after binding form data: {}", tacoOrder);

                            BindingResult bindingResult = new BeanPropertyBindingResult(tacoOrder, "tacoOrder");

                            log.info("Validating TacoOrder: {}", tacoOrder);
                            validator.validate(tacoOrder, bindingResult);
                            log.info("Validation completed. Errors found: {}", bindingResult.getErrorCount());

                            if (bindingResult.hasErrors()) {
                                bindingResult.getAllErrors().forEach(error ->
                                        log.error("Validation error: field={}, message={}", error.getObjectName(), error.getDefaultMessage())
                                );
                                return ServerResponse.ok()
                                        .contentType(MediaType.TEXT_HTML)
                                        .render("orderForm", Map.of(
                                                "tacoOrder", tacoOrder,
                                                "org.springframework.validation.BindingResult.tacoOrder", bindingResult));
                            }

                            return request.principal()
                                    .flatMap(principal -> tacoOrderService
                                            .processOrder(principal.getName(), tacoOrder))
                                    .flatMap(savedOrder -> {
                                        session.getAttributes().remove("tacoOrder");
                                        return ServerResponse.seeOther(URI.create("/orders/orderList")).build();
                                    });
                        })
                );
    }

    public Mono<ServerResponse> ordersForUser(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> tacoOrderService
                        .findOrdersForUser(principal.getName(), 20)
                        .collectList()
                        .flatMap(orders -> {
                            log.info("🔍 Orders for user {}: {}", principal.getName(), orders);
                            return ServerResponse.ok()
                                    .contentType(MediaType.TEXT_HTML)
                                    .render("orderList", Map.of("orders", orders));
                        }));
    }
}
