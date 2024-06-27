package pt.galina.spring_webflux_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.spring_webflux_demo.converter.IngredientByIdConverter;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    private final IngredientByIdConverter ingredientByIdConverter;

    public WebFluxConfig(IngredientByIdConverter ingredientByIdConverter) {
        this.ingredientByIdConverter = ingredientByIdConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(ingredientByIdConverter);
    }

    @Bean
    public RouterFunction<ServerResponse> indexRouter() {
        return RouterFunctions.route(GET("/"),
                request -> ok().contentType(MediaType.TEXT_HTML).render("home")); // Отобразить шаблон "home"
    }

}
