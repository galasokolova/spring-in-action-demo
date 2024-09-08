package pt.galina.commonresources;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class StockQuote {
    private String symbol;
    private BigDecimal price;
    private Instant timestamp;   // a timestamp that the price was valid
}

