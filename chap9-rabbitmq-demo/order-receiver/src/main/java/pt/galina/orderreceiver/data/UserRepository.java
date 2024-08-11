package pt.galina.orderreceiver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.orderreceiver.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
