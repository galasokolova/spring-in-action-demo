package pt.galina.clientreactive.config.actuator.metrics;

import pt.galina.clientreactive.entity.taco.Taco;

public class TacoCreatedEvent {
    private final Taco taco;

    public TacoCreatedEvent(Taco taco) {
        this.taco = taco;
    }

    public Taco getTaco() {
        return taco;
    }
}

