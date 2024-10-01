package pt.galina.clientreactive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pt.galina.clientreactive.entity.admin.Admin;
import pt.galina.clientreactive.entity.taco.Ingredient;
import pt.galina.clientreactive.entity.taco.data.IngredientRepository;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.clientreactive.config")
public class ClientReactiveApplication {

    private final ResourceLoader resourceLoader;

    public ClientReactiveApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientReactiveApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo,
                                        ObjectMapper objectMapper,
                                        ReactiveMongoTemplate reactiveMongoTemplate) {
        return args -> {
            try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
                List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
                ingredientRepo.saveAll(ingredients)
                        .thenMany(ingredientRepo.findAll())
                        .subscribe();
            } catch (IOException e) {
                log.error("⏩ Failed to load ingredients", e);
            }

            reactiveMongoTemplate.collectionExists(Admin.class)
                    .flatMap(exists -> {
                        if (!exists) {
                            return reactiveMongoTemplate.createCollection(Admin.class);
                        }
                        return Mono.empty();
                    })
                    .then(reactiveMongoTemplate.findOne(
                            Query.query(Criteria.where("username").is("admin")), Admin.class))
                    .switchIfEmpty(Mono.defer(() -> {
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        Admin admin = new Admin(encoder.encode("admin"), "admin");
                        return reactiveMongoTemplate.save(admin)
                                .doOnSuccess(a -> log.info("Admin created with username: admin and password: admin"));
                    }))
                    .doOnSuccess(a -> log.info("Admin already exists."))
                    .subscribe();
        };
    }
}
