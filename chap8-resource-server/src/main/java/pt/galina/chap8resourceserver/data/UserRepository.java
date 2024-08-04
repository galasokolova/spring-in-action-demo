package pt.galina.chap8resourceserver.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8resourceserver.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
