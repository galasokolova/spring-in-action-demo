package pt.galina.dockercontainerdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.dockercontainerdemo.handler.*;

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
    private final DiscountHandler discountHandler;

    public RouterFunctionConfig(DesignTacoHandler designTacoHandler,
                                OrderHandler orderHandler,
                                RegistrationHandler registrationHandler,
                                UserHandler userHandler,
                                LoginHandler loginHandler, DiscountHandler discountHandler) {
        this.designTacoHandler = designTacoHandler;
        this.orderHandler = orderHandler;
        this.registrationHandler = registrationHandler;
        this.userHandler = userHandler;
        this.loginHandler = loginHandler;

        this.discountHandler = discountHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .add(designTacoRoutes())
                .add(orderRoutes())
                .add(registrationRoutes())
                .add(userRoutes())
                .add(loginRoutes())
                .add(discountRoutes(discountHandler)) // Добавляем маршруты для дискаунта
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

    public RouterFunction<ServerResponse> discountRoutes(DiscountHandler discountHandler) {
        return route(GET("/api/discount"), discountHandler::getDiscount)
                .andRoute(POST("/api/discount"), discountHandler::updateDiscount);
    }


    private RouterFunction<ServerResponse> userRoutes() {
        return route(GET("/users"), userHandler::listUsers);
    }

    private RouterFunction<ServerResponse> loginRoutes() {
        return route(GET("/login"), loginHandler::login);
    }
}
