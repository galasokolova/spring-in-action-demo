package pt.galina.kafka_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafka_listener.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
