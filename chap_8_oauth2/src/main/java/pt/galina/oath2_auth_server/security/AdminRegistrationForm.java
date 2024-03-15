package pt.galina.oath2_auth_server.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import pt.galina.oath2_auth_server.entity.admin.Admin;

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
