package pt.galina.spring_webflux_demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            log.error("Login error: Invalid username or password");
            model.addAttribute("error", "Invalid username or password.");
        }
        log.info("Login page requested");
        return "login";
    }
}
