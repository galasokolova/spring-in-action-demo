package pt.galina.chap_18_googlecloud.security.csrf;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CustomServerCsrfTokenRepository implements ServerCsrfTokenRepository {

    private final MongoCsrfTokenRepository csrfTokenRepository;

    public CustomServerCsrfTokenRepository(MongoCsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Override
    public Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
        String tokenValue = UUID.randomUUID().toString();
        CsrfToken token = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", tokenValue);
        return Mono.just(token);
    }

    @Override
    public Mono<Void> saveToken(ServerWebExchange exchange, CsrfToken token) {
        return exchange.getSession().flatMap(session -> {
            if (token != null) {
                // Сохраняем токен в MongoDB
                MongoCsrfToken mongoToken = new MongoCsrfToken(session.getId(), token.getToken());
                return csrfTokenRepository.save(mongoToken).then();
            } else {
                // Удаляем токен из MongoDB, если он null
                return csrfTokenRepository.deleteBySessionId(session.getId());
            }
        });
    }

    @Override
    public Mono<CsrfToken> loadToken(ServerWebExchange exchange) {
        // Загружаем токен из MongoDB
        return exchange.getSession().flatMap(session ->
                csrfTokenRepository.findBySessionId(session.getId())
                        .map(tokenDoc -> new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", tokenDoc.getToken()))
        );
    }
}
