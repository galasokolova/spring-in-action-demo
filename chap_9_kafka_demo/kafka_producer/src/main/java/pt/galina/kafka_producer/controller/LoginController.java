package pt.galina.kafka_producer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }
}
