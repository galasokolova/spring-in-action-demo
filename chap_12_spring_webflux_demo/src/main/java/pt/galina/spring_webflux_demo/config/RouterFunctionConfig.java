package pt.galina.spring_webflux_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.data.TacoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final TacoRepository tacoRepo;
    private final IngredientRepository ingredientRepo;

    @Autowired
    public RouterFunctionConfig(TacoRepository tacoRepo, IngredientRepository ingredientRepo) {
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepo;
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
        return ingredientRepo.findAll().collectList().flatMap(ingredients -> {
            Map<String, Object> model = new HashMap<>();
            model.put("taco", new Taco());
            model.put("wrap", filterByType(ingredients, Ingredient.Type.WRAP));
            model.put("protein", filterByType(ingredients, Ingredient.Type.PROTEIN));
            model.put("cheese", filterByType(ingredients, Ingredient.Type.CHEESE));
            model.put("veggies", filterByType(ingredients, Ingredient.Type.VEGGIES));
            model.put("sauce", filterByType(ingredients, Ingredient.Type.SAUCE));
            return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("design", model);
        });
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}