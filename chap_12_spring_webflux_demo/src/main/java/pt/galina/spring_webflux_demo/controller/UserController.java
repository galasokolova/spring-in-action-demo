//package pt.galina.spring_webflux_demo.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import pt.galina.spring_webflux_demo.data.UserRepository;
//import pt.galina.spring_webflux_demo.entity.user.User;
//import reactor.core.publisher.Flux;
//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//    private final UserRepository userRepo;
//
//    public UserController(UserRepository userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    @GetMapping
//    public String listUsers(Model model) {
//        Flux<User> users = userRepo.findAll();
//        model.addAttribute("users", users);
//        return "userList";
//    }
//}
