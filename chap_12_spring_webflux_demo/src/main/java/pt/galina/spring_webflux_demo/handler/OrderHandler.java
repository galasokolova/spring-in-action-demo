package pt.galina.spring_webflux_demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.service.TacoOrderService;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class OrderHandler {

    private final TacoOrderService tacoOrderService;

    @Autowired
    public OrderHandler(TacoOrderService tacoOrderService) {
        this.tacoOrderService = tacoOrderService;
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
                                    return ServerResponse.ok()
                                            .contentType(MediaType.TEXT_HTML)
                                            .render("orderForm", Map.of("tacoOrder", order));
                                });
                    });
                });
    }

    public Mono<ServerResponse> processOrder(ServerRequest request) {
        return request.session()
                .flatMap(session -> {
                    TacoOrder tacoOrder = session.getAttribute("tacoOrder");
                    if (tacoOrder == null) {
                        return ServerResponse.badRequest().build();
                    }

                    return request.principal()
                            .flatMap(principal -> tacoOrderService.processOrder(principal.getName(), tacoOrder))
                            .flatMap(savedOrder -> {
                                session.getAttributes().remove("tacoOrder");
                                return ServerResponse.ok().contentType(MediaType.TEXT_HTML)
                                        .render("orderList", Map.of("order", savedOrder));
                            });
                });
    }

    public Mono<ServerResponse> ordersForUser(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> tacoOrderService.findOrdersForUser(principal.getName(), 20)
                        .collectList()
                        .flatMap(orders -> ServerResponse.ok().contentType(MediaType.TEXT_HTML)
                                .render("orderList", Map.of("orders", orders))));
    }
}
