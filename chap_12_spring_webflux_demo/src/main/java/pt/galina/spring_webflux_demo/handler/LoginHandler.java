package pt.galina.spring_webflux_demo.handler;

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
        return request.queryParam("error")
                .map(error -> {
                    log.error("Login error: Invalid username or password");
                    model.put("error", "Invalid username or password.");
                    return Mono.just(model);
                })
                .orElseGet(() -> {
                    log.info("Login page requested");
                    return Mono.just(model);
                })
                .flatMap(m -> ServerResponse.ok().render("login", m));
    }
}
