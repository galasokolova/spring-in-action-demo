//package pt.galina.chap_18_googlecloud.security.csrf;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.web.server.csrf.CsrfToken;
//import org.springframework.security.web.server.csrf.DefaultCsrfToken;
//import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.UUID;
//
//@Slf4j
//public class CustomServerCsrfTokenRepository implements ServerCsrfTokenRepository {
//
//    private final MongoCsrfTokenRepository csrfTokenRepository;
//
//    public CustomServerCsrfTokenRepository(MongoCsrfTokenRepository csrfTokenRepository) {
//        this.csrfTokenRepository = csrfTokenRepository;
//    }
//
//    @Override
//    public Mono<CsrfToken> generateToken(ServerWebExchange exchange) {
//        return exchange.getSession()
//                .flatMap(session -> {
//                    String tokenValue = UUID.randomUUID().toString();
//                    CsrfToken token = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", tokenValue);
//                    MongoCsrfToken mongoCsrfToken = new MongoCsrfToken(session.getId(), tokenValue);
//                    return csrfTokenRepository.save(mongoCsrfToken)
//                            .thenReturn(token);
//                });
//    }
//
//    @Override
//    public Mono<Void> saveToken(ServerWebExchange exchange, CsrfToken token) {
//        return exchange.getSession().flatMap(session -> {
//            if (token != null) {
//                // Сохраняем новый токен в БД
//                MongoCsrfToken mongoToken = new MongoCsrfToken(session.getId(), token.getToken());
//                log.info("Saving CSRF token for sessionId: {}", session.getId());
//                return csrfTokenRepository.save(mongoToken)
//                        .doOnSuccess(savedToken -> log.info("CSRF token successfully saved for sessionId: {}", session.getId()))
//                        .doOnError(error -> log.error("Error saving CSRF token for sessionId: {}", session.getId(), error))
//                        .then();
//            } else {
//                // Удаляем токен
//                log.info("Deleting CSRF token for sessionId: {}", session.getId());
//                return csrfTokenRepository.deleteBySessionId(session.getId())
//                        .doOnSuccess(aVoid -> log.info("CSRF token successfully deleted for sessionId: {}", session.getId()))
//                        .doOnError(error -> log.error("Error deleting CSRF token for sessionId: {}", session.getId(), error))
//                        .then();
//            }
//        });
//    }
//
//    @Override
//    public Mono<CsrfToken> loadToken(ServerWebExchange exchange) {
//        return exchange.getSession()
//                .flatMap(session -> csrfTokenRepository.findBySessionId(session.getId())
//                        .map(mongoToken -> (CsrfToken) new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", mongoToken.getToken()))
//                        .switchIfEmpty(Mono.defer(() -> generateToken(exchange)))
//                );
//    }
//}
