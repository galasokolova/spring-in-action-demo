package pt.galina.clientnonreactive.data;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.galina.clientnonreactive.entity.admin.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}

