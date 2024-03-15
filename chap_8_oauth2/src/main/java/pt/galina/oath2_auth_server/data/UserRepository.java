package pt.galina.oath2_auth_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.oath2_auth_server.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
