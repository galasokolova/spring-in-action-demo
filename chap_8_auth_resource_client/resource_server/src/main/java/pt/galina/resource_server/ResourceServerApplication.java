package pt.galina.resource_server;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.resource_server.data.UserRepository;
import pt.galina.resource_server.entity.user.User;

@SpringBootApplication
public class ResourceServerApplication {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public ResourceServerApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
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

}
