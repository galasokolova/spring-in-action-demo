package pt.galina.oath2_auth_server.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.oath2_auth_server.entity.admin.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUsername(String username);
}
