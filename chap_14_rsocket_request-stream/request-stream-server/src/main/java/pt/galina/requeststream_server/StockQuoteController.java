package pt.galina.requeststream_server;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import pt.galina.commonresources.StockQuote;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Controller
public class StockQuoteController {
    @MessageMapping("stock/{symbol}")
    public Flux<StockQuote> getStockPrice(@DestinationVariable("symbol") String symbol) {
        return Flux.interval(Duration.ofSeconds(1))

                .map(i -> { BigDecimal price = BigDecimal.valueOf(Math.random() * 10);  //price is calculated as a random value
                    return new StockQuote(symbol, price, Instant.now());
                });
    }
}

