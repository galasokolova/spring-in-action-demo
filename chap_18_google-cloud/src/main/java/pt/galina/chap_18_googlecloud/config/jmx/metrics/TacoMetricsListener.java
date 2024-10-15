package pt.galina.chap_18_googlecloud.config.jmx.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pt.galina.chap_18_googlecloud.config.jmx.metrics.TacoCreatedEvent;
import pt.galina.chap_18_googlecloud.entity.taco.Taco;

@Component
public class TacoMetricsListener {

    private final MeterRegistry meterRegistry;

    public TacoMetricsListener(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @EventListener
    public void handleTacoCreatedEvent(TacoCreatedEvent event) {
        Taco taco = event.getTaco();
        taco.getIngredients().forEach(ingredient ->
                meterRegistry.counter("tacocloud", "ingredient", ingredient.getId()).increment()
        );
    }
}


