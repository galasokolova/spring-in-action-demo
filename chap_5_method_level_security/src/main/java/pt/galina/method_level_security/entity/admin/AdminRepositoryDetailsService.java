package pt.galina.method_level_security.entity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminRepositoryDetailsService implements UserDetailsService {

    private final AdminRepository adminRepo;

    @Autowired
    public AdminRepositoryDetailsService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Admin admin = adminRepo.findByUsername(username);
        if (admin != null) {
            return admin;
        }
        throw new UsernameNotFoundException(
                "Admin '" + username + "' not found");
    }
}
