package pt.galina.taco_kitchen.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.taco_kitchen.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
