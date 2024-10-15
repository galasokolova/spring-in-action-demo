package pt.galina.chap_18_googlecloud.security.csrf;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MongoCsrfTokenRepository extends ReactiveMongoRepository<MongoCsrfToken, String> {

    Mono<MongoCsrfToken> findBySessionId(String sessionId);

    Mono<Void> deleteBySessionId(String sessionId);
}


