package pt.galina.spring_webflux_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class WebFluxConfig {

    @Bean
    public RouterFunction<ServerResponse> indexRouter() {
        return RouterFunctions.route(GET("/"),
                request -> ok().contentType(MediaType.TEXT_HTML).render("home")); // Отобразить шаблон "home"
    }

    @Bean
    public RouterFunction<ServerResponse> loginRouter() {
        return RouterFunctions.route(GET("/login"), this::handleLogin);
    }

    private Mono<ServerResponse> handleLogin(ServerRequest request) {
        return request.queryParam("error")
                .map(error -> ok().contentType(MediaType.TEXT_HTML)
                        .render("login", "error", true))
                .orElse(ok().contentType(MediaType.TEXT_HTML).render("login"));
    }
}
