//package pt.galina.spring_webflux_demo.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import pt.galina.spring_webflux_demo.data.UserRepository;
//import pt.galina.spring_webflux_demo.security.RegistrationForm;
//import reactor.core.publisher.Mono;
//
//@Controller
//@RequestMapping("/register")
//public class RegistrationController {
//    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
//    private final UserRepository userRepo;
//    private final PasswordEncoder passwordEncoder;
//
//    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping
//    public String registerForm() {
//        return "registration";
//    }
//
//    @PostMapping
//    public Mono<String> processRegistration(RegistrationForm form) {
//        log.info("\uD83D\uDC7D Processing registration for user: {}", form.getUsername());
//
//        return userRepo.save(form.toUser(passwordEncoder))
//                .doOnSuccess(user -> log.info("\uD83D\uDE0E Saved user: {}", user))
//                .then(Mono.just("redirect:/login"));
//    }
//}
//
