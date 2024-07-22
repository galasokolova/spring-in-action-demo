package pt.galina.chap5oauth2client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/checkRoles")
    public String checkRoles(Authentication authentication, Model model) {
        model.addAttribute("roles", authentication.getAuthorities());
        return "checkRoles";
    }
}
