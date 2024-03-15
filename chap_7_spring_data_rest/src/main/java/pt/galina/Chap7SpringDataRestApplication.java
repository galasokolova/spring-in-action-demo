package pt.galina;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pt.galina.spring_data_rest.client.IngredientClient;
import pt.galina.spring_data_rest.data.IngredientRepository;
import pt.galina.spring_data_rest.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

/*
to get endpoints:
            $ curl localhost:8080/data-api
to request the first page of tacos where the page size is 5
            $ curl "localhost:8080/data-api/tacos?size=5"
to request the second page of tacos
            $ curl "localhost:8080/data-api/tacos?size=5&page=1"
to sort the resulting list by any property of the entity, for example, the 12 most recently created tacos for the UI to display
            $ curl "localhost:8080/data-api/tacos?sort=createdAt,desc&page=0&size=12"
 */
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.spring_data_rest.config")
public class Chap7SpringDataRestApplication {
	private final ResourceLoader resourceLoader;

    public Chap7SpringDataRestApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public static void main(String[] args) {

		SpringApplication.run(Chap7SpringDataRestApplication.class, args);
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
