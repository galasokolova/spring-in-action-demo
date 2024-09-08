package pt.galina.fireandforget_client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import pt.galina.fireandforgetcommon.Alert;
import pt.galina.fireandforgetcommon.Utils;

import java.time.Instant;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            log.info("Creating RSocket requester...");
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

            Alert alert = new Alert(Alert.Level.RED, "Craig", Instant.now());

            log.info("Sending alert: level={}, sender={}, timestamp={}",
                    alert.getLevel(), alert.getOrderedBy(), alert.getOrderedAt());

            tcp
                    .route("alert")
                    .data(alert)
                    .send()
                    .subscribe();

            log.info("Alert sent");

            Utils.sleep(10000);
        };
    }
}
