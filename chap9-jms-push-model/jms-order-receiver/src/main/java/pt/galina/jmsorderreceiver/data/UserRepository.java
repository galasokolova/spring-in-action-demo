package pt.galina.jmsorderreceiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jmsorderreceiver.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
