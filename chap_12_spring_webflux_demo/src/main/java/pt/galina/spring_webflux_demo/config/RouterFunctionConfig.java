package pt.galina.spring_webflux_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.handler.DesignTacoHandler;
import pt.galina.spring_webflux_demo.handler.OrderHandler;
import pt.galina.spring_webflux_demo.handler.RegistrationHandler;
import pt.galina.spring_webflux_demo.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final DesignTacoHandler designTacoHandler;
    private final OrderHandler orderHandler;
    private final RegistrationHandler registrationHandler;
    private final UserHandler userHandler;

    public RouterFunctionConfig(DesignTacoHandler designTacoHandler,
                                OrderHandler orderHandler,
                                RegistrationHandler registrationHandler,
                                UserHandler userHandler) {
        this.designTacoHandler = designTacoHandler;
        this.orderHandler = orderHandler;
        this.registrationHandler = registrationHandler;
        this.userHandler = userHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .add(designTacoRoutes())
                .add(orderRoutes())
                .add(registrationRoutes())
                .add(userRoutes())
                .build();
    }

    private RouterFunction<ServerResponse> designTacoRoutes() {
        return route(GET("/design"), designTacoHandler::showDesignForm)
                .andRoute(POST("/design"), designTacoHandler::processTaco)
                .andRoute(GET("/orders/current"), orderHandler::showOrderForm);
    }

    private RouterFunction<ServerResponse> orderRoutes() {
        return route(GET("/orders/current"), orderHandler::showOrderForm)
                .andRoute(POST("/orders"), orderHandler::processOrder)
                .andRoute(GET("/orders/orderList"), orderHandler::ordersForUser);
    }

    private RouterFunction<ServerResponse> registrationRoutes() {
        return route(POST("/register"), registrationHandler::processRegistration)
                .andRoute(GET("/register"), request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("registration"))
                .andRoute(GET("/login"), request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("login"));
    }

    private RouterFunction<ServerResponse> userRoutes() {
        return route(GET("/users"), userHandler::listUsers);
    }
}
