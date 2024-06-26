package pt.galina.spring_webflux_demo.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.spring_webflux_demo.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
