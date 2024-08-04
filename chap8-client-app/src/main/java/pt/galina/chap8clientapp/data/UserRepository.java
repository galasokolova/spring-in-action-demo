package pt.galina.chap8clientapp.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8clientapp.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);

}
