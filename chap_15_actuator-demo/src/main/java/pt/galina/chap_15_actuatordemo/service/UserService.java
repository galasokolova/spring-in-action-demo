package pt.galina.chap_15_actuatordemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.galina.chap_15_actuatordemo.entity.user.User;
import pt.galina.chap_15_actuatordemo.entity.user.data.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setFullname(user.getFullname());
                    existingUser.setStreet(user.getStreet());
                    existingUser.setCity(user.getCity());
                    existingUser.setState(user.getState());
                    existingUser.setZip(user.getZip());
                    existingUser.setPhone(user.getPhone());
                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
