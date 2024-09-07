package pt.galina.requestresponse_server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class GreetingController {
    @MessageMapping("greeting/{name}")
    public Mono<String> handleGreeting(@DestinationVariable("name") String name, Mono<String> greetingMono) {
        log.info("Handling greeting for: {}", name);
        return greetingMono

                .doOnNext(greeting -> log.info("Received a greeting from {}: {}", name, greeting))
                .map(greeting -> "Hello to you, too, " + name)
                .doOnError(error -> log.error("Error occurred: {}", error.getMessage()))
                .onErrorReturn("Error occurred");
    }


}
