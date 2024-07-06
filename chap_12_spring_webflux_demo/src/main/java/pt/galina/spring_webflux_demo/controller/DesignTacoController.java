//package pt.galina.spring_webflux_demo.controller;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.support.SessionStatus;
//import pt.galina.spring_webflux_demo.data.IngredientRepository;
//import pt.galina.spring_webflux_demo.data.OrderRepository;
//import pt.galina.spring_webflux_demo.data.TacoRepository;
//import pt.galina.spring_webflux_demo.data.UserRepository;
//import pt.galina.spring_webflux_demo.dto.TacoDTO;
//import pt.galina.spring_webflux_demo.dto.TacoOrderDTO;
//import pt.galina.spring_webflux_demo.converter.TacoDtoConverter;
//import pt.galina.spring_webflux_demo.converter.TacoOrderDtoConverter;
//import pt.galina.spring_webflux_demo.entity.taco.Taco;
//import pt.galina.spring_webflux_demo.entity.taco.TacoOrder;
//import pt.galina.spring_webflux_demo.entity.user.User;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.security.Principal;
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/design")
//@SessionAttributes("tacoOrder")
//public class DesignTacoController {
//    private final IngredientRepository ingredientRepo;
//    private final UserRepository userRepo;
//    private final TacoDtoConverter tacoConverter;
//    private final TacoOrderDtoConverter tacoOrderConverter;
//    private final TacoRepository tacoRepo;
//    private final OrderRepository orderRepo;
//
//    @Autowired
//    public DesignTacoController(IngredientRepository ingredientRepo, UserRepository userRepo,
//                                TacoDtoConverter tacoConverter, TacoOrderDtoConverter tacoOrderConverter, TacoRepository tacoRepo, OrderRepository orderRepo) {
//        this.ingredientRepo = ingredientRepo;
//        this.userRepo = userRepo;
//        this.tacoConverter = tacoConverter;
//        this.tacoOrderConverter = tacoOrderConverter;
//        this.tacoRepo = tacoRepo;
//        this.orderRepo = orderRepo;
//    }
//
//    @ModelAttribute(name = "tacoOrder")
//    public TacoOrder order() {
//        return new TacoOrder();
//    }
//
//    @ModelAttribute(name = "taco")
//    public Taco taco() {
//        return new Taco();
//    }
//
//    @ModelAttribute(name = "user")
//    public Mono<User> user(Principal principal) {
//        String username = principal.getName();
//        return userRepo.findByUsername(username).cast(User.class);
//    }
//
//    @PostMapping
//    public Mono<String> processTaco(@Valid @ModelAttribute TacoDTO tacoDTO, Errors errors,
//                                    @ModelAttribute TacoOrderDTO orderDTO, SessionStatus sessionStatus, Model model) {
//        if (errors.hasErrors()) {
//            model.addAttribute("errors", errors);
//            log.error("Validation errors: {}", errors);
//            return Mono.just("design");
//        }
//
//        log.info("Processing taco: {}", tacoDTO);
//
//        return Flux.fromIterable(tacoDTO.getIngredients())
//                .flatMap(ingredientRepo::findById)
//                .collectList()
//                .flatMap(ingredients -> {
//                    log.info("Ingredients found: {}", ingredients);
//                    return tacoConverter.toEntity(tacoDTO, ingredients);
//                })
//                .flatMap(taco -> {
//                    log.info("Taco entity created: {}", taco);
//                    return tacoRepo.save(taco)
//                            .flatMap(savedTaco -> {
//                                log.info("Taco saved: {}", savedTaco);
//                                return tacoOrderConverter.toEntity(orderDTO)
//                                        .flatMap(order -> {
//                                            order.addTaco(savedTaco);
//                                            log.info("Taco added to order: {}", savedTaco);
//                                            return Mono.just(order);
//                                        });
//                            });
//                })
//                .flatMap(order -> {
//                    log.info("Order entity updated: {}", order);
//                    // Сохраняем TacoOrder в базу данных
//                    return orderRepo.save(order)
//                            .flatMap(savedOrder -> {
//                                log.info("Order saved: {}", savedOrder);
//                                sessionStatus.setComplete();
//                                return Mono.just("redirect:/orders/current");
//                            });
//                })
//                .doOnError(e -> log.error("Error processing taco order", e));
//    }
//
//}