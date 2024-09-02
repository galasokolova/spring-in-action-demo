package pt.galina.cassandrareactivepersistence.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.cassandrareactivepersistence.handler.OrderHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class OrderRouter {

    @Bean
    public RouterFunction<ServerResponse> orderRoutes(OrderHandler orderHandler) {
        return route()
                .GET("/orders/current", orderHandler::showOrderForm)
                .POST("/orders", orderHandler::processOrder)
                .GET("/orders/orderList", orderHandler::ordersForUser)
                .build();
    }
}
