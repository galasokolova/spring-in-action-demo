package pt.galina.mongodbreactivepersistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.mongodbreactivepersistence.handler.DesignTacoHandler;
import pt.galina.mongodbreactivepersistence.handler.OrderHandler;
import pt.galina.mongodbreactivepersistence.handler.RegistrationHandler;
import pt.galina.mongodbreactivepersistence.handler.UserHandler;
import pt.galina.mongodbreactivepersistence.handler.LoginHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final DesignTacoHandler designTacoHandler;
    private final OrderHandler orderHandler;
    private final RegistrationHandler registrationHandler;
    private final UserHandler userHandler;
    private final LoginHandler loginHandler;

    public RouterFunctionConfig(DesignTacoHandler designTacoHandler,
                                OrderHandler orderHandler,
                                RegistrationHandler registrationHandler,
                                UserHandler userHandler,
                                LoginHandler loginHandler) {
        this.designTacoHandler = designTacoHandler;
        this.orderHandler = orderHandler;
        this.registrationHandler = registrationHandler;
        this.userHandler = userHandler;
        this.loginHandler = loginHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .add(designTacoRoutes())
                .add(orderRoutes())
                .add(registrationRoutes())
                .add(userRoutes())
                .add(loginRoutes())
                .build();
    }

    private RouterFunction<ServerResponse> designTacoRoutes() {
        return route(GET("/design"), designTacoHandler::showDesignForm)
                .andRoute(POST("/design"), designTacoHandler::processTaco);
    }

    private RouterFunction<ServerResponse> orderRoutes() {
        return route(GET("/orders/current"), orderHandler::showOrderForm)
                .andRoute(POST("/orders"), orderHandler::processOrder)
                .andRoute(GET("/orders/orderList"), orderHandler::ordersForUser);
    }

    private RouterFunction<ServerResponse> registrationRoutes() {
        return route(POST("/register"), registrationHandler::processRegistration)
                .andRoute(GET("/register"), request -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("registration"));
    }

    private RouterFunction<ServerResponse> userRoutes() {
        return route(GET("/users"), userHandler::listUsers);
    }

    private RouterFunction<ServerResponse> loginRoutes() {
        return route(GET("/login"), loginHandler::login);
    }
}
