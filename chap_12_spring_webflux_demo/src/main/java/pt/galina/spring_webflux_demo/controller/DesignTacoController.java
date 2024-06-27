package pt.galina.spring_webflux_demo.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pt.galina.spring_webflux_demo.data.UserRepository;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.entity.user.User;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final UserRepository userRepo;

    @Autowired
    public DesignTacoController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "user")
    public Mono<User> user(Principal principal) {
        String username = principal.getName();
        return userRepo.findByUsername(username).cast(User.class);
    }

    @GetMapping
    public String showDesignForm(Model model) {
        log.info("Showing design form");
        return "design";
    }

    @PostMapping
    public Mono<String> processTaco(SessionStatus sessionStatus,
                                    @Valid @ModelAttribute Taco taco, Errors errors,
                                    @ModelAttribute TacoOrder order, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return Mono.just("design");
        }

        order.addTaco(taco);
        model.addAttribute("order", order);
        sessionStatus.setComplete();

        return Mono.just("redirect:/orders/current");
    }
}
