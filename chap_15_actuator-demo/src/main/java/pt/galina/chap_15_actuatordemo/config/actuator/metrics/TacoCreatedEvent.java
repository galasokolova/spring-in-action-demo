package pt.galina.chap_15_actuatordemo.config.actuator.metrics;

import pt.galina.chap_15_actuatordemo.entity.taco.Taco;

public class TacoCreatedEvent {
    private final Taco taco;

    public TacoCreatedEvent(Taco taco) {
        this.taco = taco;
    }

    public Taco getTaco() {
        return taco;
    }
}

