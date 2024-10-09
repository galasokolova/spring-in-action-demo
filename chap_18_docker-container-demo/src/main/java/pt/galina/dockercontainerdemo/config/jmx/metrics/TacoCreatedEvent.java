package pt.galina.dockercontainerdemo.config.jmx.metrics;


import pt.galina.dockercontainerdemo.entity.taco.Taco;

public class TacoCreatedEvent {
    private final Taco taco;

    public TacoCreatedEvent(Taco taco) {
        this.taco = taco;
    }

    public Taco getTaco() {
        return taco;
    }
}

