package pt.galina.message_sender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.message_sender.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
