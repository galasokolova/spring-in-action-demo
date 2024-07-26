package pt.galina.chap7restfulcontroller.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap7restfulcontroller.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
