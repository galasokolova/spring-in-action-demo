package pt.galina.ordersender.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.ordersender.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
