package pt.galina.mongodbreactivepersistence.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.mongodbreactivepersistence.handler.DesignTacoHandler;

@Configuration
public class DesignTacoRouter {
    private final DesignTacoHandler handler;

    public DesignTacoRouter(DesignTacoHandler handler) {
        this.handler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> designTacoRoutes() {
        return RouterFunctions.route()
                .GET("/design", handler::showDesignForm)
                .POST("/design", handler::processTaco)
                .build();
    }
}

