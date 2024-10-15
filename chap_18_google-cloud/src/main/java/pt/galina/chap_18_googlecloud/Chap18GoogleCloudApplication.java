package pt.galina.chap_18_googlecloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import pt.galina.chap_18_googlecloud.entity.taco.Ingredient;
import pt.galina.chap_18_googlecloud.entity.taco.data.IngredientRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.chap_18_googlecloud.config")
public class Chap18GoogleCloudApplication {

    private final ResourceLoader resourceLoader;

    public Chap18GoogleCloudApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public static void main(String[] args) {
        SpringApplication.run(Chap18GoogleCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo,
                                        ObjectMapper objectMapper, ApplicationContext context) {
        return args -> {
            try {
                // Announce that the application is temporarily not ready to serve traffic
                AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);

                // Data loading
                try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
                    List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
                    ingredientRepo.saveAll(ingredients)
                            .thenMany(ingredientRepo.findAll())
                            .subscribe();
                }

                // When data loading is complete, announce that the application is ready to serve traffic
                AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC);
                log.info("✅ Application is ready and accepting traffic");

            } catch (IOException e) {
                log.error("⏩ Failed to load ingredients", e);

                // In case of an error, mark the application as not live
                AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
            }
        };
    }

}
