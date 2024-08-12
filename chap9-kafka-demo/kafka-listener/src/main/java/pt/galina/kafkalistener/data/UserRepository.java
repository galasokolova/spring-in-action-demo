package pt.galina.kafkalistener.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.kafkalistener.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
