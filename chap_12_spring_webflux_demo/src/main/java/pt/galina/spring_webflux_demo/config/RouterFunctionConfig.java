package pt.galina.spring_webflux_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import pt.galina.spring_webflux_demo.data.TacoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final TacoRepository tacoRepo;

    @Autowired
    public RouterFunctionConfig(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/api/tacos").and(queryParam("recent", Objects::nonNull)), this::recent)
                .andRoute(POST("/api/tacos"), this::postTaco)
                .andRoute(GET("/"), this::home)
                .andRoute(GET("/design"), this::design);
    }

    public Mono<ServerResponse> recent(ServerRequest request) {
        return ServerResponse.ok()
                .body(tacoRepo.findAll().take(10), Taco.class);
    }

    public Mono<ServerResponse> postTaco(ServerRequest request) {
        return request.bodyToMono(Taco.class)
                .flatMap(tacoRepo::save)
                .flatMap(savedTaco -> ServerResponse.created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId()))
                        .bodyValue(savedTaco));
    }

    public Mono<ServerResponse> home(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("home");
    }

    public Mono<ServerResponse> design(ServerRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("taco", new Taco()); // Ensure Taco object is present in model
        // Add any additional model attributes if needed
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model);
    }
}
