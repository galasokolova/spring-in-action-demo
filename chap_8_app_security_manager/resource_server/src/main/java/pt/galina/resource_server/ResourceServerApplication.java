package pt.galina.resource_server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.resource_server.data.IngredientRepository;
import pt.galina.resource_server.data.UserRepository;
import pt.galina.resource_server.entity.taco.Ingredient;
import pt.galina.resource_server.entity.user.User;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class ResourceServerApplication {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public ResourceServerApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader() {
        return args -> {
            userRepo.save(new User(
                    "habuma",
                    encoder.encode("password"),
                    "John Doe",
                    "1234 North Street",
                    "Cross Roads",
                    "TX",
                    "76227",
                    "123-123-1234"));
            userRepo.save(new User(
                    "tacochef",
                    encoder.encode("password"),
                    "Taco Chef",
                    "1234 Taco Street",
                    "Taco City",
                    "TX",
                    "76227",
                    "111-222-3333"));
        };
    }

    @Bean
    public CommandLineRunner ingredientDataLoader(IngredientRepository ingredientRepo, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        return args -> {
            try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
                List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<List<Ingredient>>() {
                });
                ingredientRepo.saveAll(ingredients);
            }
        };
    }

}
