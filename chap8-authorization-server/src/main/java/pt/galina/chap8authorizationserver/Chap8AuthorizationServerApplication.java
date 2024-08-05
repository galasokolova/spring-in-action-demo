package pt.galina.chap8authorizationserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.chap8authorizationserver.user.User;
import pt.galina.chap8authorizationserver.user.UserRepository;


@SpringBootApplication
public class Chap8AuthorizationServerApplication {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public Chap8AuthorizationServerApplication(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Chap8AuthorizationServerApplication.class, args);
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
/*

fEmzgP0oWN3NID2grr8k7T2PZ2Kw70Qz0nJdXIuKC34QlvPHGOEqXz0jA7AS8RQCVKgjcJpTAJfvm-FiOecHbiaHhFcDAVSYdjFyDUUrfN3-Puwxhh1TqndgA7sH4P2d

 */


