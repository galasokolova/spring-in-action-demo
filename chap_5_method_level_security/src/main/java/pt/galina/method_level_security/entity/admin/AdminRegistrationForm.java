package pt.galina.method_level_security.entity.admin;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;

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
