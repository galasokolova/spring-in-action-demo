package pt.galina.chap_17_jmxmonitoring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_17_jmxmonitoring.handler.RegistrationHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RegistrationRouter {
    private final RegistrationHandler handler;

    public RegistrationRouter(RegistrationHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> registrationRoutes() {
        return route(GET("/register"), handler::showRegistrationForm)
                .andRoute(POST("/register"), handler::processRegistration);
    }
}
