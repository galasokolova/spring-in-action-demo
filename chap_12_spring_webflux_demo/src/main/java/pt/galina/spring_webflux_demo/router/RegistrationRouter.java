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
    private final RegistrationHandler registrationHandler;

    public RegistrationRouter(RegistrationHandler registrationHandler) {
        this.registrationHandler = registrationHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> registrationRoutes() {
        return route(GET("/register"), registrationHandler::showRegistrationForm)
                .andRoute(POST("/register"), registrationHandler::processRegistration);
    }
}
