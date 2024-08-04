package pt.galina.chap8admin_preauthorize_demo.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import pt.galina.chap8admin_preauthorize_demo.entity.admin.Admin;

@Data
public class AdminRegistrationForm {
    private String username;
    private String password;

    public Admin toUser(@ModelAttribute PasswordEncoder passwordEncoder) {
        return new Admin(
                username,
                passwordEncoder.encode(password)
        );
    }
}
