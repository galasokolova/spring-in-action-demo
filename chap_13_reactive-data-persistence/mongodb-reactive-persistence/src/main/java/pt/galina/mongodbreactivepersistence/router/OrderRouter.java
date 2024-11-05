package pt.galina.mongodbreactivepersistence.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.mongodbreactivepersistence.handler.OrderHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class OrderRouter {
    private final OrderHandler orderHandler;

    public OrderRouter(OrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> orderRoutes() {
        return route()
                .GET("/orders/current", orderHandler::showOrderForm)
                .POST("/orders", orderHandler::processOrder)
                .GET("/orders/orderList", orderHandler::ordersForUser)
                .build();
    }
}
