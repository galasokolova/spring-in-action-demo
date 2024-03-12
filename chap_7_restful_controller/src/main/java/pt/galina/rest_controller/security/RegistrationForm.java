package pt.galina.rest_controller.security;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.rest_controller.entity.user.User;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    public User toUser(@NotNull PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password),
                fullname,
                street,
                city,
                state,
                zip,
                phone
        );
    }
}
