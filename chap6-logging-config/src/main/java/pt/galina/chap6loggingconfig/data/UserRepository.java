package pt.galina.chap6loggingconfig.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap6loggingconfig.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
