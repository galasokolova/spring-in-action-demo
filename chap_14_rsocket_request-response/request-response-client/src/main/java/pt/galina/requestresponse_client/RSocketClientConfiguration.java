package pt.galina.requestresponse_client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import pt.galina.requestresponse_client.util.Utils;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            log.info("Attempting to create RSocket requester...");
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

            String who = "Craig";

            log.info("Sending request to server for: {}", who);

            tcp
                    .route("greeting/{name}", who)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .doOnSubscribe(subscription -> log.info("Subscription started"))
                    .subscribe(
                            response -> log.info("Got a response: {}", response),
                            error -> log.error("Error occurred: {}", error.getMessage())
                    );
            Utils.sleep(5000);  // to make the client wait for the response

            log.info("Client finished work after waiting.");
        };
    }
}




