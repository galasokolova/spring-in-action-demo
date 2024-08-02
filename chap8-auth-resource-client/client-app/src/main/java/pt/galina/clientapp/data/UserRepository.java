package pt.galina.clientapp.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.clientapp.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);

}
