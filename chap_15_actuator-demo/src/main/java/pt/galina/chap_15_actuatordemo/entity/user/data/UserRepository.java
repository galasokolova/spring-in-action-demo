package pt.galina.chap_15_actuatordemo.entity.user.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.chap_15_actuatordemo.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
