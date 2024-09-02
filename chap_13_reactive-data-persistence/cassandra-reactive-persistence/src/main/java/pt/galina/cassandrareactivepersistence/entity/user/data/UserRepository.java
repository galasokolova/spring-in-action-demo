package pt.galina.cassandrareactivepersistence.entity.user.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import pt.galina.cassandrareactivepersistence.entity.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCassandraRepository<User, String> {

    Mono<User> findByUsername(String username);
}
