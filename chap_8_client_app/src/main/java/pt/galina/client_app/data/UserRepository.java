package pt.galina.client_app.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.client_app.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
