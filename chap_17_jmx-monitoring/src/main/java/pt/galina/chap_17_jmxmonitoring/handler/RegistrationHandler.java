package pt.galina.chap_17_jmxmonitoring.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.chap_17_jmxmonitoring.security.RegistrationForm;
import pt.galina.chap_17_jmxmonitoring.service.UserService;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class RegistrationHandler {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationHandler(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<ServerResponse> showRegistrationForm(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("registration");
    }

    public Mono<ServerResponse> processRegistration(ServerRequest request) {
        return request.formData()
                .flatMap(formData -> {
                    String username = formData.toSingleValueMap().get("username");
                    String password = formData.toSingleValueMap().get("password");
                    String fullname = formData.toSingleValueMap().get("fullname");
                    String street = formData.toSingleValueMap().get("street");
                    String city = formData.toSingleValueMap().get("city");
                    String state = formData.toSingleValueMap().get("state");
                    String zip = formData.toSingleValueMap().get("zip");
                    String phone = formData.toSingleValueMap().get("phone");

                    RegistrationForm form = new RegistrationForm();
                    form.setUsername(username);
                    form.setPassword(password);
                    form.setFullname(fullname);
                    form.setStreet(street);
                    form.setCity(city);
                    form.setState(state);
                    form.setZip(zip);
                    form.setPhone(phone);

                    log.info("👽 Processing registration for user: {}", username);

                    return userService.createUser(form.toUser(passwordEncoder))
                            .doOnSuccess(user -> log.info("😎 Saved user: {}", user))
                            .then(ServerResponse.seeOther(URI.create("/login")).build());
                });
    }
}
