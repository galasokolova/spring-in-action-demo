package pt.galina.clientreactive.config.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Calendar;
@Component
public class WackoHealthIndicator  implements HealthIndicator {
    @Override
    public Health health() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); //Getting current hour (24-h) with Calendar
        if (hour > 12) {  // This health indicator first checks what the current time is, and if it’s after noon,
            return Health
                    .outOfService()  // returns a health status of OUT_OF_SERVICE
                    .withDetail("reason", "I’m out of service after lunchtime") // with a few details explaining the reason for that status
                    .withDetail("hour", hour)  //adding current hour
                    .build();
        }
        if (Math.random() <= 0.1) { //If a random number (0 - 1) less or equal to 0.1 (probability 10%)
            return Health
                    .down() // Returning status DOWN
                    .withDetail("reason", "I break 10% of the time")
                    .build();
        }
        return Health
                .up()
                .withDetail("reason", "All is good!")
                .build();
    }
}

