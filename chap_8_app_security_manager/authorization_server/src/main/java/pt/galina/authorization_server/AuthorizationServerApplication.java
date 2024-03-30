package pt.galina.authorization_server;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.authorization_server.user.User;
import pt.galina.authorization_server.user.UserRepository;

@SpringBootApplication
public class AuthorizationServerApplication {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthorizationServerApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader() {
        return args -> {
            userRepo.save(new User(
                    "habuma",
                    encoder.encode("password"),
                    "mabuma",
                    "street",
                    "city",
                    "state",
                    "111",
                    "111111"));
            userRepo.save(new User(
                    "tacochef",
                    encoder.encode("password"),
                    "chef",
                    "avenue",
                    "parede",
                    "marvel",
                    "222",
                    "22222"));
        };
    }

}
