package pt.galina.dockercontainerdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import pt.galina.dockercontainerdemo.entity.taco.Ingredient;
import pt.galina.dockercontainerdemo.entity.taco.data.IngredientRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.dockercontainerdemo.config")
public class Chap18DockerContainerDemoApplication {

    private final ResourceLoader resourceLoader;

    public Chap18DockerContainerDemoApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public static void main(String[] args) {
        SpringApplication.run(Chap18DockerContainerDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo,
                                        ObjectMapper objectMapper) {
        return args -> {
            try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
                List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
                ingredientRepo.saveAll(ingredients)
                        .thenMany(ingredientRepo.findAll())
                        .subscribe();
            } catch (IOException e) {
                log.error("⏩ Failed to load ingredients", e);
            }
        };
    }

}
