package pt.galina;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.auth_server.user.TacoUserRepository;
import pt.galina.auth_server.user.User;


/*
http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients
 */
@SpringBootApplication
public class Chap8AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap8AuthorizationServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(
            TacoUserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(
                    new User("habuma", encoder.encode("password"), "ROLE_USER"));
            repo.save(
                    new User("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
        };
    }

}
