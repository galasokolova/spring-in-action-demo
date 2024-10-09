package pt.galina.dockercontainerdemo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.dockercontainerdemo.config.jmx.DiscountProperties;
import reactor.core.publisher.Mono;

@Component
public class DiscountHandler {

    private final DiscountProperties discountProperties;

    public DiscountHandler(DiscountProperties discountProperties) {
        this.discountProperties = discountProperties;
    }

    public Mono<ServerResponse> getDiscount(ServerRequest request) {
        return ServerResponse.ok().bodyValue(discountProperties.getCode());
    }

    public Mono<ServerResponse> updateDiscount(ServerRequest request) {
        return request.bodyToMono(String.class)
                .flatMap(newDiscountCode -> {
                    discountProperties.setCode(newDiscountCode);
                    return ServerResponse.ok().bodyValue("Discount code updated to: " + newDiscountCode);
                });
    }
}


