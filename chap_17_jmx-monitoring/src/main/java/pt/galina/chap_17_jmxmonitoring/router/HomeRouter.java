package pt.galina.chap_17_jmxmonitoring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_17_jmxmonitoring.handler.HomeHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class HomeRouter {

    @Bean
    public RouterFunction<ServerResponse> homeRoute(HomeHandler homeHandler) {
        return route(GET("/"), homeHandler::home);
    }
}
