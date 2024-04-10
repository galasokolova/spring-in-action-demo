package pt.galina.rabbitmq.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rabbitmq.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
