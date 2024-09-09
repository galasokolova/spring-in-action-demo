package pt.galina.channel_client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import pt.galina.channelcommonresources.GratuityIn;
import pt.galina.channelcommonresources.GratuityOut;
import pt.galina.channelcommonresources.Utils;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {

            log.info("Creating RSocket requester...");
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

            Flux<GratuityIn> gratuityInFlux = Flux.fromArray(new GratuityIn[] {
                            new GratuityIn(BigDecimal.valueOf(35.50), 18),
                            new GratuityIn(BigDecimal.valueOf(10.00), 15),
                            new GratuityIn(BigDecimal.valueOf(23.25), 20),
                            new GratuityIn(BigDecimal.valueOf(52.75), 18),
                            new GratuityIn(BigDecimal.valueOf(80.00), 15)
                    })
                    .delayElements(Duration.ofSeconds(1))
                    .doOnNext(gratuityIn -> log.info("Sending gratuity: billTotal={}, percent={}",
                            gratuityIn.getBillTotal(), gratuityIn.getPercent()));

            tcp.route("gratuity")
                    .data(gratuityInFlux)
                    .retrieveFlux(GratuityOut.class)
                    .doOnNext(out -> log.info("Received response: {}% gratuity on {} is {}",
                            out.getPercent(), out.getBillTotal(), out.getGratuity()))
                    .doOnError(error -> log.error("Error occurred: {}", error.getMessage()))
                    .subscribe();

            log.info("Started sending gratuity data...");

            Utils.sleep(10000);
        };
    }
}


