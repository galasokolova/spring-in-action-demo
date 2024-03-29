package pt.galina.method_level_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.galina.method_level_security.entity.admin.Admin;
import pt.galina.method_level_security.entity.admin.AdminRepository;
import pt.galina.method_level_security.entity.user.User;
import pt.galina.method_level_security.entity.user.UserRepository;

import java.security.Principal;

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
