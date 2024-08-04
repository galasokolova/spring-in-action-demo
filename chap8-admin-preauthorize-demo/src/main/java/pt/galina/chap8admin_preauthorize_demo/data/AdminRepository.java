package pt.galina.chap8admin_preauthorize_demo.data;

import org.springframework.data.repository.CrudRepository;
import pt.galina.chap8admin_preauthorize_demo.entity.admin.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUsername(String username);
}
