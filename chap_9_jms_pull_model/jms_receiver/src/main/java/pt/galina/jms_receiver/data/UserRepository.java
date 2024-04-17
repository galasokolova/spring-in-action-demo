package pt.galina.jms_receiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.jms_receiver.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
