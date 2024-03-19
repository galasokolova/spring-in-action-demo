package pt.galina.auth_server.user;

import org.springframework.data.repository.CrudRepository;

public interface TacoUserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
