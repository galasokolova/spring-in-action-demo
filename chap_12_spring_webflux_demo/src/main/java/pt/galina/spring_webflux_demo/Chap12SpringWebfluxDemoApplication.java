package pt.galina.spring_webflux_demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.data.TacoRepository;
import pt.galina.spring_webflux_demo.entity.taco.Ingredient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.spring_webflux_demo.config")
public class Chap12SpringWebfluxDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(Chap12SpringWebfluxDemoApplication.class);

    private final ResourceLoader resourceLoader;

    public Chap12SpringWebfluxDemoApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {

        SpringApplication.run(Chap12SpringWebfluxDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo, ObjectMapper objectMapper) {
        return args -> {
            try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
                List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
                ingredientRepo.saveAll(ingredients)
                        .thenMany(ingredientRepo.findAll())
                        .subscribe();
            } catch (IOException e) {
                log.error("⏩⏩⏩ Failed to load ingredients", e);
            }
        };
    }
}
