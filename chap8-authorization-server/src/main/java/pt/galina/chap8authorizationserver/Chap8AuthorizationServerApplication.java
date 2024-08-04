package pt.galina.chap8authorizationserver;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.chap8authorizationserver.user.User;
import pt.galina.chap8authorizationserver.user.UserRepository;

/*
http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients+deleteIngredients


$ curl localhost:9000/oauth2/token \
 -H"Content-type: application/x-www-form-urlencoded" \
 -d"grant_type=authorization_code" \
 -d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client" \
 -d"code=$code" \
 -u taco-admin-client:secret

 */
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

eyJraWQiOiIwNjQyNGIyZC01ODNkLTQ1MWEtYjY5OC1lZjJlOWE3ZDYyNDUiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ0YWNvY2hlZiIsImF1ZCI6InRhY28tYWRtaW4tY2xpZW50IiwibmJmIjoxNzExMzkyOTE1LCJzY29wZSI6WyJkZWxldGVJbmdyZWRpZW50cyIsIndyaXRlSW5ncmVkaWVudHMiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDAwIiwiZXhwIjoxNzExMzkzMjE1LCJpYXQiOjE3MTEzOTI5MTUsImp0aSI6IjczMTJiNWVmLTkzMzAtNDdlZi1iM2MwLTAwZmFhZGM3NjAzNSJ9.DUfjg_l5xFCazkxXN5N3TGH5b9HYCeq_UMxeXjHPPTSCDodCEhMYkWNqTAbpImoepCE6zG2UUGzFroe6ePWGA1QoNygV4E-DYEgV5Vw0kTgl9IM_eGLnq3QW9eN4q4enNUEowleGq0CTQVRYMJ7DwJL-gcAwpTXdbiq5p9NQGs2y4Wqo68oaiPF9X5hNZtotVF0YvgJcPD8M35AZidhwBZu9TVCrsBA-ZoDvCX58mEHiIxW0XPPPnEzlDy6SqKYe89tpfs65NKqJiXF0q9nsd--in8_CiOH9esPa6ORk697H6QEtkaMJk5uR3ZmHddMBnm7qaHiHLIk0ULHUOSHUpw



 */


