package pt.galina.rsocketwebsocket_server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class GreetingController {

    @MessageMapping("greeting")
    public Mono<String> greet(String name) {
        return Mono.just("Hello, " + name + "!");
    }
}

