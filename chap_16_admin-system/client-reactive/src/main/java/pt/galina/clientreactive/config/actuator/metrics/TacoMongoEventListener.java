package pt.galina.clientreactive.config.actuator.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import pt.galina.clientreactive.entity.taco.Taco;

@Slf4j
@Component
public class TacoMongoEventListener extends AbstractMongoEventListener<Taco> {

    private final MeterRegistry meterRegistry;

    public TacoMongoEventListener(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Taco> event) {
        Taco taco = event.getSource();
        meterRegistry.counter("tacocloud", "ingredient", taco.getId()).increment();
        log.info("Тако успешно сохранено: {}", taco.getId());
    }
}


