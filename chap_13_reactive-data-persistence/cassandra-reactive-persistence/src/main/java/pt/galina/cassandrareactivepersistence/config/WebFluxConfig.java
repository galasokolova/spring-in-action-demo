package pt.galina.cassandrareactivepersistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import pt.galina.cassandrareactivepersistence.converter.IdToIngredientUDTConverter;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {

    private final IdToIngredientUDTConverter ingredientByIdConverter;

    @Autowired
    public WebFluxConfig(IdToIngredientUDTConverter ingredientByIdConverter) {
        this.ingredientByIdConverter = ingredientByIdConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(ingredientByIdConverter);
    }
}

