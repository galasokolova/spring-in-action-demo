package pt.galina.clientnonreactive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.clientnonreactive.data.AdminRepository;
import pt.galina.clientnonreactive.data.IngredientRepository;
import pt.galina.clientnonreactive.entity.admin.Admin;
import pt.galina.clientnonreactive.entity.taco.Ingredient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.clientnonreactive.config")
public class ClientNonReactiveApplication {

	private final ResourceLoader resourceLoader;

	public ClientNonReactiveApplication(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientNonReactiveApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository ingredientRepo,
										AdminRepository adminRepository,
										PasswordEncoder passwordEncoder,
										ObjectMapper objectMapper) {
		return args -> {
			// Загрузка ингредиентов
			try (InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream()) {
				List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
				ingredientRepo.saveAll(ingredients);
				log.info("Ingredients loaded successfully.");
			} catch (IOException e) {
				log.error("Failed to load ingredients", e);
			}

			// Создание администратора в базе данных H2
			if (adminRepository.findByUsername("admin").isEmpty()) {
				Admin admin = new Admin("admin", passwordEncoder.encode("admin"));
				adminRepository.save(admin);
				log.info("Admin created with username: admin and password: admin");
			} else {
				log.info("Admin already exists.");
			}
		};
	}
}
