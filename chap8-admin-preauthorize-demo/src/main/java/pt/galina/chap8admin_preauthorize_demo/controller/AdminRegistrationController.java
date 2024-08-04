package pt.galina.chap8admin_preauthorize_demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.galina.chap8admin_preauthorize_demo.data.AdminRepository;
import pt.galina.chap8admin_preauthorize_demo.security.AdminRegistrationForm;

@Controller
@RequestMapping("/adminReg")
public class AdminRegistrationController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminRegistrationController(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "adminReg";
    }


    @PostMapping
    public String processAdminRegistration(@ModelAttribute AdminRegistrationForm form) {
        adminRepository.save(form.toUser(passwordEncoder));
        return "redirect:/admin";
    }
}

