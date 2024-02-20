package pt.galina.chap_5_user_in_db.in_db.security;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.chap_5_user_in_db.in_db.entity.user.User;

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
