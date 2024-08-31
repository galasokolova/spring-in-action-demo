package pt.galina.mongodbreactivepersistence.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pt.galina.mongodbreactivepersistence.entity.user.data.UserRepository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UserHandler {

    private final UserRepository userRepo;

    public UserHandler(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Mono<ServerResponse> listUsers(ServerRequest request) {
        return userRepo.findAll()
                .collectList()
                .flatMap(users -> ServerResponse.ok().render("userList", Map.of("users", users)));
    }
}
