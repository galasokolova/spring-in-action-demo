package pt.galina.clientnonreactive.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.galina.clientnonreactive.data.UserRepository;
import pt.galina.clientnonreactive.entity.user.User;

@Service
public class UserRepositoryDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserRepositoryDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            User user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");

    }
}
