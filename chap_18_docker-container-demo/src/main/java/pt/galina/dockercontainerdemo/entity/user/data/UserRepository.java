package pt.galina.dockercontainerdemo.entity.user.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pt.galina.dockercontainerdemo.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
