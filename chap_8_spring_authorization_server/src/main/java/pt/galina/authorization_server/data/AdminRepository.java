package pt.galina.authorization_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.authorization_server.entity.admin.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUsername(String username);
}
