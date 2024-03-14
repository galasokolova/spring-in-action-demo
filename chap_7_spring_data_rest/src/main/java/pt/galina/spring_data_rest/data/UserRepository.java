package pt.galina.spring_data_rest.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.spring_data_rest.entity.user.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
