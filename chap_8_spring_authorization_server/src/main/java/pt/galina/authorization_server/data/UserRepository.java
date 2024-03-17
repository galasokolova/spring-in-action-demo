package pt.galina.authorization_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.authorization_server.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
