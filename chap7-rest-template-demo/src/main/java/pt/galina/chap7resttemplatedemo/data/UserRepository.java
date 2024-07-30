package pt.galina.chap7resttemplatedemo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap7resttemplatedemo.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
