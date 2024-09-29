package pt.galina.clientreactive.config.actuator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pt.galina.clientreactive.entity.taco.Taco;

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


