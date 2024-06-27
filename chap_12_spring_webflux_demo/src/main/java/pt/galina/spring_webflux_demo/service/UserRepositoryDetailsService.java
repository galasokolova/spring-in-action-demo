package pt.galina.spring_webflux_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pt.galina.spring_webflux_demo.data.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class UserRepositoryDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepo;

    @Autowired
    public UserRepositoryDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userRepo.findByUsername(username)
                .map(UserDetails.class::cast)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User '" + username + "' not found")));
    }
}
