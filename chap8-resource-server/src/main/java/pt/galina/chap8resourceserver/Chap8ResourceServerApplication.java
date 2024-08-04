package pt.galina.chap8resourceserver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.chap8resourceserver.data.IngredientRepository;
import pt.galina.chap8resourceserver.data.UserRepository;
import pt.galina.chap8resourceserver.entity.taco.Ingredient;
import pt.galina.chap8resourceserver.entity.user.User;

import java.io.InputStream;
import java.util.List;

/*
http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients

Request for token:
$ curl localhost:9000/oauth2/token \
 -H"Content-type: application/x-www-form-urlencoded" \
 -d"grant_type=authorization_code" \
 -d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client" \
 -d"code=1S_K3QqsK19uf4KiTzqSEH0lB9vlQNtptwxFmlc4bm3KxHy-MM0Tw4vgfK1amvB9dzuPs77pArGFgBsQuLPvnL7sCwWegQnhebTznbL9kjhPH2Mo6aW-_dXVFEQoYGix" \
 -u taco-admin-client:secret


curl -X GET http://localhost:8080/api/ingredients \
-H "Authorization: Bearer "


$ curl http://localhost:8080/api/ingredients \
 -H "Content-type: application/json" \
 -d "{\"id\":\"SHMP\", \"name\":\"Coconut Shrimp\", \"type\":\"PROTEIN\"}" \
 -H "Authorization: Bearer "

 curl localhost:9000/oauth2/token \
-H "Content-type: application/x-www-form-urlencoded" \
-d "grant_type=refresh_token&refresh_token=<refresh_token>" \
-u taco-admin-client:secret

 */
@SpringBootApplication
public class Chap8ResourceServerApplication {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public Chap8ResourceServerApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chap8ResourceServerApplication.class, args);
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
