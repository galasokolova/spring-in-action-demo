package pt.galina.rabbitmq_push_listener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq_push_listener.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
