package pt.galina.rabbitmq_order_sender;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pt.galina.rabbitmq_order_sender.data.IngredientRepository;
import pt.galina.rabbitmq_order_sender.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.rabbitmq_order_sender.config")
public class RabbitmqOrderSenderApplication {

    private final ResourceLoader resourceLoader;

    public RabbitmqOrderSenderApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqOrderSenderApplication.class, args);
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

/*
http://dockerhost:15672

docker run -d --hostname dockerhost --name rabbit-name rabbitmq:3

 */