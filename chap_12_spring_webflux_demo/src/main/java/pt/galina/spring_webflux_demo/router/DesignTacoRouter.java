package pt.galina.spring_webflux_demo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.handler.DesignTacoHandler;

@Configuration
public class DesignTacoRouter {
    private final DesignTacoHandler designTacoHandler;

    public DesignTacoRouter(DesignTacoHandler designTacoHandler) {
        this.designTacoHandler = designTacoHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> designTacoRoutes() {
        return RouterFunctions.route()
                .GET("/design", designTacoHandler::showDesignForm)
                .POST("/design", designTacoHandler::processTaco)
                .build();
    }
}

