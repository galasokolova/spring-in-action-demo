package pt.galina.fireandforget_server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import pt.galina.fireandforgetcommon.Alert;
import reactor.core.publisher.Mono;
@Controller
@Slf4j
public class AlertController {
    @MessageMapping ("alert")     // setAlert() handles messages Alert with the rout "alert".
    public Mono<Void> setAlert(Mono<Alert> alertMono) {    // returns Mono<Void> indicating that there is no response
        return alertMono.doOnNext(alert ->
                        log.info("{} alert ordered by {} at {}", //To keep things simple , it logs only the alerts
                                alert.getLevel(),
                                alert.getOrderedBy(),
                                alert.getOrderedAt())
                )
                .thenEmpty(Mono.empty());
    }
}

