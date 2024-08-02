package pt.galina.chap7resttemplatedemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import pt.galina.chap7resttemplatedemo.data.IngredientRepository;
import pt.galina.chap7resttemplatedemo.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;


@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.chap7resttemplatedemo.config")
public class Chap7RestTemplateDemoApplication {

    private final ResourceLoader resourceLoader;

    public Chap7RestTemplateDemoApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chap7RestTemplateDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, ObjectMapper objectMapper) {
        return args -> {
            InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream();
            List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            repo.saveAll(ingredients);
        };
    }

}
