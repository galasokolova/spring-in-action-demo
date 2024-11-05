package pt.galina.mongodbreactivepersistence.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.mongodbreactivepersistence.handler.HomeHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HomeRouter {
    private final HomeHandler homeHandler;

    public HomeRouter(HomeHandler homeHandler) {
        this.homeHandler = homeHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> homeRoute() {
        return route(GET("/"), homeHandler::home);
    }
}
