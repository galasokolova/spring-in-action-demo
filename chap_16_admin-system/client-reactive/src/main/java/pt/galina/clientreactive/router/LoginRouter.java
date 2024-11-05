package pt.galina.clientreactive.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.clientreactive.handler.LoginHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoginRouter {
    private final LoginHandler loginHandler;

    public LoginRouter(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> loginRoute() {
        return route(GET("/login"), loginHandler::login);
    }
}
