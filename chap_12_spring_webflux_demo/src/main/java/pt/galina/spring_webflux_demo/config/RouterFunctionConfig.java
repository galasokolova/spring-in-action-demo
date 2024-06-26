package pt.galina.spring_webflux_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.data.TacoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {   //  RouterFunctionConfig is instead of TacoController

    private final TacoRepository tacoRepo;

    @Autowired
    public RouterFunctionConfig(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {  //  RouterFunction connects HTTP-requests with handlers
        return route(GET("/api/tacos")    // will get the last 10 taco from repository.
                .and(queryParam("recent", Objects::nonNull)), this::recent)
                .andRoute(POST("/api/tacos"), this::postTaco); // saves a new taco and retrieves it with 201 (CREATED).
    }
    public Mono<ServerResponse> recent(ServerRequest request) { // request may be useful in future, there is no usage now
        return ServerResponse
                .ok()
                .body(tacoRepo.findAll().take(10), // takes the last 10 taco from repo. retrieves them with 200 (OK).
                        Taco.class);
    }
    public Mono <ServerResponse> postTaco(ServerRequest request) { // takes taco object from request body, saves it to repo.
        return request.bodyToMono(Taco.class)
                .flatMap(tacoRepo::save)
                .flatMap(savedTaco  ->  {
                    return ServerResponse    // returns saved Taco in response with 201 (CREATED).
                            .created(URI.create("http://localhost:8080/api/tacos/" +  savedTaco.getId()))
                            .body(savedTaco, Taco.class);
                });
    }
}

