package pt.galina.chap6profileconfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pt.galina.chap6profileconfig.config.OrderProps;
import pt.galina.chap6profileconfig.data.IngredientRepository;
import pt.galina.chap6profileconfig.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(OrderProps.class) // instead of @ConfigurationPropertiesScan("pt.galina.chap6profileconfig.config")
public class Chap6ProfileConfigApplication {

    private final ResourceLoader resourceLoader;

    public Chap6ProfileConfigApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public static void main(String[] args) {
        SpringApplication.run(Chap6ProfileConfigApplication.class, args);
    }

    @Bean
    @Profile("!prod")
    public CommandLineRunner dataLoader(IngredientRepository repo, ObjectMapper objectMapper) {
        return args -> {
            InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream();
            List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            repo.saveAll(ingredients);
        };
    }

}
