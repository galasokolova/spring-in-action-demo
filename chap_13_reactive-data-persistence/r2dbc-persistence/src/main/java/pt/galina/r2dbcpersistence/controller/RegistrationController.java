package pt.galina.r2dbcpersistence.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pt.galina.r2dbcpersistence.entity.user.RegistrationForm;
import pt.galina.r2dbcpersistence.entity.user.data.UserRepository;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Mono<String> registerForm() {
        return Mono.just("registration");
    }

    @PostMapping
    public Mono<String> processRegistration(RegistrationForm form) {
        return userRepo.save(form.toUser(passwordEncoder))
                .then(Mono.just("redirect:/login"));
    }
}

