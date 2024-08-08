package pt.galina.jmsordersender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jmsordersender.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
