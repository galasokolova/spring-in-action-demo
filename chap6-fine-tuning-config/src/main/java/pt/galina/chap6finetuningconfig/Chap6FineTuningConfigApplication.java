package pt.galina.chap6finetuningconfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import pt.galina.chap6finetuningconfig.data.IngredientRepository;
import pt.galina.chap6finetuningconfig.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class Chap6FineTuningConfigApplication {

    private final ResourceLoader resourceLoader;

    public Chap6FineTuningConfigApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chap6FineTuningConfigApplication.class, args);
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
