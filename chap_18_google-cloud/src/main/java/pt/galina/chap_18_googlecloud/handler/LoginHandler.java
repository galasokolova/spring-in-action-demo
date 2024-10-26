package pt.galina.chap_18_googlecloud.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    public Mono<ServerResponse> login(ServerRequest request) {
        Map<String, Object> model = new HashMap<>();
        boolean hasError = request.queryParam("error").isPresent();
        if (hasError) {
            log.error("Login error: Invalid username or password");
            model.put("error", "Invalid username or password.");
        } else {
            log.info("Login page requested");
        }

        return request.session()
                .doOnNext(session -> {
                    // Можно добавить дополнительные данные в сессию, если нужно
                    session.getAttributes().putIfAbsent("authenticated", false);
                })
                .then(ServerResponse.ok().render("login", model));
    }
}
