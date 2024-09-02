package pt.galina.cassandrareactivepersistence.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.galina.cassandrareactivepersistence.entity.user.User;
import pt.galina.cassandrareactivepersistence.entity.user.UserUDT;

import java.util.UUID;

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

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(UUID.randomUUID().toString())  // Генерация уникального id
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullname(fullname)
                .address(new UserUDT(street, city, state, zip, phone))
                .build();
    }
}
