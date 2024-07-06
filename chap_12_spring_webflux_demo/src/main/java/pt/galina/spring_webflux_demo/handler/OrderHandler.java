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
        // Получает текущую сессию
        return request
                .session()
                .flatMap(session -> {
                    // Получает контекст безопасности и извлекает аутентификацию
                    Mono<Authentication> authenticationMono = ReactiveSecurityContextHolder.getContext()
                            .map(SecurityContext::getAuthentication);

                    // Обрабатывает аутентификацию
                    return authenticationMono.flatMap(authentication -> {
                        // Извлекает имя пользователя из аутентификации
                        String username = authentication.getName();
                        // Извлекает текущий заказ из сессии
                        TacoOrder existingOrder = session.getAttribute("tacoOrder");

                        // Находит или создает заказ для пользователя
                        return tacoOrderService
                                .findOrCreateOrder(username, existingOrder)
                                .flatMap(order -> {
                                    // Если существующего заказа нет, сохраняет новый заказ в сессии
                                    if (existingOrder == null) {
                                        session.getAttributes()
                                                .put("tacoOrder", order);
                                    }
                                    // Возвращает ответ с формой заказа
                                    return ServerResponse
                                            .ok()
                                            .contentType(MediaType.TEXT_HTML)
                                            .render(
                                                    "orderForm",
                                                    Map.of("tacoOrder", order));
                                });
                    });
                });
    }


    public Mono<ServerResponse> processOrder(ServerRequest request) {
        // Получает текущую сессию
        return request.session()
                .flatMap(session -> {
                    // Извлекает заказ тако из сессии
                    TacoOrder tacoOrder = session.getAttribute("tacoOrder");

                    // Если заказа нет, возвращает ответ с ошибкой
                    if (tacoOrder == null) {
                        return ServerResponse
                                .badRequest()
                                .build();
                    }

                    // Обрабатывает заказ, получая имя пользователя из аутентификации
                    return request
                            .principal()
                            .flatMap(principal -> tacoOrderService
                                    .processOrder(principal.getName(), tacoOrder))
                            .flatMap(savedOrder -> {
                                // Удаляет заказ из сессии после сохранения
                                session
                                        .getAttributes()
                                        .remove("tacoOrder");
                                // Возвращает ответ с отображением списка заказов
                                return ServerResponse
                                        .ok()
                                        .contentType(MediaType.TEXT_HTML)
                                        .render(
                                                "orderList",
                                                Map.of("order", savedOrder));
                            });
                });
    }


    public Mono<ServerResponse> ordersForUser(ServerRequest request) {
        // Получает имя пользователя из аутентификации
        return request
                .principal()
                .flatMap(principal -> tacoOrderService
                        // Находит заказы для пользователя, ограничиваясь 20 заказами
                        .findOrdersForUser(principal.getName(), 20)
                        .collectList()
                        // Возвращает ответ с отображением списка заказов
                        .flatMap(orders -> ServerResponse
                                .ok()
                                .contentType(MediaType.TEXT_HTML)
                                .render(
                                        "orderList",
                                        Map.of("orders", orders))));
    }

}
