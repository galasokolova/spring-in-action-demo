package pt.galina.message_sender;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pt.galina.message_sender.data.IngredientRepository;
import pt.galina.message_sender.entity.taco.Ingredient;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.message_sender.config")
public class JmsMessageSenderApplication {

	private final ResourceLoader resourceLoader;

	public JmsMessageSenderApplication(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(JmsMessageSenderApplication.class, args);
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
