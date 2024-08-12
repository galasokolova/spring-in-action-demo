package pt.galina.kafkaproducer.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafkaproducer.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
