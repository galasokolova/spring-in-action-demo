package pt.galina.chap_15_actuatordemo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_15_actuatordemo.handler.LoginHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoginRouter {

    @Bean
    public RouterFunction<ServerResponse> loginRoute(LoginHandler loginHandler) {
        return route(GET("/login"), loginHandler::login);
    }
}