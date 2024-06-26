package pt.galina.spring_webflux_demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.galina.spring_webflux_demo.data.UserRepository;
import pt.galina.spring_webflux_demo.security.RegistrationForm;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

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
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public Mono<String> processRegistration(RegistrationForm form, ServerWebExchange exchange) {
        return userRepo.save(form.toUser(passwordEncoder))
                .then(Mono.defer(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                    exchange.getResponse().getHeaders().setLocation(URI.create("/login"));
                    return Mono.just("redirect:/login");
                }));
    }
}
