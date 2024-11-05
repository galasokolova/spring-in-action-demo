package pt.galina.dockercontainerdemo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.dockercontainerdemo.handler.DiscountHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class DiscountRouter {

    private final DiscountHandler discountHandler;

    public DiscountRouter(DiscountHandler discountHandler) {
        this.discountHandler = discountHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(GET("/api/discount"), discountHandler::getDiscount)
                .andRoute(POST("/api/discount"), discountHandler::updateDiscount);
    }
}
