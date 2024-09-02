package pt.galina.cassandrareactivepersistence.entity.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.galina.cassandrareactivepersistence.entity.user.User;
import pt.galina.cassandrareactivepersistence.entity.user.data.UserRepository;
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
        // Проверка на уникальность username
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> {
                    // Если пользователь с таким username существует, выбрасываем ошибку
                    return Mono.<User>error(new RuntimeException("Username already exists"));
                })
                .switchIfEmpty(userRepository.save(user)); // Иначе сохраняем нового пользователя
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
                    // Обновляем данные пользователя
                    existingUser.setUsername(user.getUsername());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setFullname(user.getFullname());
                    existingUser.setAddress(user.getAddress());
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