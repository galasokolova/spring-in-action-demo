package pt.galina.r2dbcpersistence.entity.user.web;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pt.galina.r2dbcpersistence.entity.user.data.UserRepository;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;

@Service
public class ReactiveUserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    public ReactiveUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(user -> user);
    }
}
