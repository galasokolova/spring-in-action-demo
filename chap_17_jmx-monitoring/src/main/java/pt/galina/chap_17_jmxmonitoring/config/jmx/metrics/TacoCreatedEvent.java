package pt.galina.chap_17_jmxmonitoring.config.jmx.metrics;


import pt.galina.chap_17_jmxmonitoring.entity.taco.Taco;

public class TacoCreatedEvent {
    private final Taco taco;

    public TacoCreatedEvent(Taco taco) {
        this.taco = taco;
    }

    public Taco getTaco() {
        return taco;
    }
}

