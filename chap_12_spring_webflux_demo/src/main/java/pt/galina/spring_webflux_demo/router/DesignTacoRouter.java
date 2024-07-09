package pt.galina.spring_webflux_demo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.handler.DesignTacoHandler;

@Configuration
public class DesignTacoRouter {

    @Bean
    public RouterFunction<ServerResponse> designTacoRoutes(DesignTacoHandler handler) {
        return RouterFunctions.route()
                .GET("/design", handler::showDesignForm)
                .POST("/design", handler::processTaco)
                .build();
    }
}

