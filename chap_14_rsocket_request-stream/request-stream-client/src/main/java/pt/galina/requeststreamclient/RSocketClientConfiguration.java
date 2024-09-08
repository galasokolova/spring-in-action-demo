package pt.galina.requeststreamclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import pt.galina.commonresources.StockQuote;
import pt.galina.commonresources.Utils;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            String stockSymbol = "XYZ";

            log.info("Creating RSocket requester...");
            RSocketRequester tcp = requesterBuilder.tcp("localhost", 7000);

            log.info("Sending request to server for stock symbol: {}", stockSymbol);

            tcp
                    .route("stock/{symbol}", stockSymbol)
                    .retrieveFlux(StockQuote.class)
                    .doOnNext(stockQuote -> log.info(
                            "Price of {} : {} (at {})",
                            stockQuote.getSymbol(),
                            stockQuote.getPrice(),
                            stockQuote.getTimestamp())
                    )
                    .doOnError(error -> log.error("Error occurred: {}", error.getMessage()))
                    .subscribe();

            Utils.sleep(10000);
        };
    }
}

