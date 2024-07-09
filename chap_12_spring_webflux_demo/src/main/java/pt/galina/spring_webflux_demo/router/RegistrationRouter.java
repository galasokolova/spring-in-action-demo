package pt.galina.spring_webflux_demo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.handler.RegistrationHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RegistrationRouter {

    @Bean
    public RouterFunction<ServerResponse> registrationRoutes(RegistrationHandler handler) {
        return route(GET("/register"), handler::showRegistrationForm)
                .andRoute(POST("/register"), handler::processRegistration);
    }
}
