package pt.galina.chap5oauth2client.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap5oauth2client.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
