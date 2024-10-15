package pt.galina.chap_18_googlecloud.config.jmx.metrics;


import pt.galina.chap_18_googlecloud.entity.taco.Taco;

public class TacoCreatedEvent {
    private final Taco taco;

    public TacoCreatedEvent(Taco taco) {
        this.taco = taco;
    }

    public Taco getTaco() {
        return taco;
    }
}

