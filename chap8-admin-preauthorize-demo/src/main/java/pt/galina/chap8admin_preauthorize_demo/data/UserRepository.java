package pt.galina.chap8admin_preauthorize_demo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
