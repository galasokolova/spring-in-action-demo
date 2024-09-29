package pt.galina.clientreactive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import pt.galina.clientreactive.converter.IngredientByIdConverter;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    private final IngredientByIdConverter ingredientByIdConverter;

    @Autowired
    public WebFluxConfig(IngredientByIdConverter ingredientByIdConverter) {
        this.ingredientByIdConverter = ingredientByIdConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(ingredientByIdConverter);
    }
}
