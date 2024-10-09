package pt.galina.dockercontainerdemo.config.jmx.info;

import org.springframework.context.event.EventListener;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.galina.dockercontainerdemo.config.jmx.metrics.TacoCreatedEvent;
import pt.galina.dockercontainerdemo.entity.taco.Taco;

import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

@Service
@ManagedResource
public class TacoCounter implements NotificationPublisherAware {

    private final AtomicLong counter = new AtomicLong(0);
    private NotificationPublisher notificationPublisher;

    @EventListener // Listens to TacoCreatedEvent
    public void onTacoCreated(TacoCreatedEvent event) {
        Taco taco = event.getTaco();
        increment(1);
    }

    @ManagedOperation
    public long increment(long delta) {
        long before = counter.get();
        long after = counter.addAndGet(delta);
        if ((after / 2) > (before / 2)) {
            Notification notification = new Notification(
                    "taco.count",
                    this,
                    before,
                    "The taco count reached " + after + "!"
            );
            notificationPublisher.sendNotification(notification);
        }
        return after;
    }

    @ManagedAttribute
    public long getTacoCount() {
        return counter.get();
    }

    @Override
    public void setNotificationPublisher(@NonNull NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }
}
