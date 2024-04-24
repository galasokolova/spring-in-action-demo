package pt.galina.kafka_producer.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafka_producer.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
