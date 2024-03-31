package pt.galina.jms.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
