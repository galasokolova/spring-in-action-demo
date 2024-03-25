package pt.galina.resource_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.resource_server.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
