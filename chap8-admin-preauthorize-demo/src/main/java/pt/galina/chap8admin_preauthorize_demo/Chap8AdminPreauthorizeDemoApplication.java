package pt.galina.chap8admin_preauthorize_demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.chap8admin_preauthorize_demo.data.AdminRepository;
import pt.galina.chap8admin_preauthorize_demo.data.IngredientRepository;
import pt.galina.chap8admin_preauthorize_demo.data.UserRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.admin.Admin;
import pt.galina.chap8admin_preauthorize_demo.entity.taco.Ingredient;
import pt.galina.chap8admin_preauthorize_demo.entity.user.User;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.chap8admin_preauthorize_demo.config")
public class Chap8AdminPreauthorizeDemoApplication {

    private final ResourceLoader resourceLoader;

    public Chap8AdminPreauthorizeDemoApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public static void main(String[] args) {
        SpringApplication.run(Chap8AdminPreauthorizeDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository ingredientRepo,
            ObjectMapper objectMapper,
            ResourceLoader resourceLoader,
            UserRepository userRepo,
            AdminRepository adminRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            // Load ingredients from JSON
            InputStream inputStream = resourceLoader.getResource("classpath:ingredient.json").getInputStream();
            List<Ingredient> ingredients = objectMapper.readValue(inputStream, new TypeReference<>() {});
            ingredientRepo.saveAll(ingredients);

            // Add User
            if (userRepo.findByUsername("user") == null) {
                userRepo.save(new User(
                        "user",
                        passwordEncoder.encode("1234"),
                        "Test User",
                        "Test Street",
                        "Test City",
                        "Test State",
                        "12345",
                        "555-555-555"
                ));
            }

            // Add Admin
            if (adminRepo.findByUsername("admin") == null) {
                adminRepo.save(new Admin(
                        "admin",
                        passwordEncoder.encode("1234")
                ));
            }
        };
    }
}
