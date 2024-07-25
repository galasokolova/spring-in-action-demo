package pt.galina.rest_controller.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.rest_controller.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
