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
import pt.galina.spring_webflux_demo.data.IngredientRepository;
import pt.galina.spring_webflux_demo.data.UserRepository;
import pt.galina.spring_webflux_demo.dto.TacoDTO;
import pt.galina.spring_webflux_demo.dto.TacoOrderDTO;
import pt.galina.spring_webflux_demo.converter.TacoDtoConverter;
import pt.galina.spring_webflux_demo.converter.TacoOrderDtoConverter;
import pt.galina.spring_webflux_demo.entity.taco.Taco;
import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
import pt.galina.spring_webflux_demo.entity.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private final IngredientRepository ingredientRepo;
    private final UserRepository userRepo;
    private final TacoDtoConverter tacoConverter;
    private final TacoOrderDtoConverter tacoOrderConverter;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, UserRepository userRepo,
                                TacoDtoConverter tacoConverter, TacoOrderDtoConverter tacoOrderConverter) {
        this.ingredientRepo = ingredientRepo;
        this.userRepo = userRepo;
        this.tacoConverter = tacoConverter;
        this.tacoOrderConverter = tacoOrderConverter;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrderDTO order() {
        return new TacoOrderDTO();
    }

    @ModelAttribute(name = "taco")
    public TacoDTO taco() {
        return new TacoDTO();
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
    public Mono<String> processTaco(@Valid @ModelAttribute TacoDTO tacoDTO, Errors errors,
                                    @ModelAttribute TacoOrderDTO orderDTO, SessionStatus sessionStatus, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return Mono.just("design");
        }

        return tacoConverter.toEntity(tacoDTO)
                .flatMap(taco -> tacoOrderConverter.toEntity(orderDTO)
                        .flatMap(order -> {
                            order.addTaco(taco);
                            sessionStatus.setComplete();
                            return Mono.just("redirect:/orders/current");
                        }));
    }
}
