package pt.galina.clientnonreactive.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.clientnonreactive.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
