package pt.galina.r2dbcpersistence.entity.user.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pt.galina.r2dbcpersistence.entity.user.AppUser;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<AppUser, Long> {
    Mono<AppUser> findByUsername(String username);

}

