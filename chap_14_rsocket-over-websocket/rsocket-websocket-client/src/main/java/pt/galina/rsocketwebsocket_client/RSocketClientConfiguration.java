package pt.galina.rsocketwebsocket_client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            // CountDownLatch для ожидания завершения асинхронных операций
            CountDownLatch latch = new CountDownLatch(1);

            // Создаем WebSocket RSocketRequester
            RSocketRequester requester = requesterBuilder
                    .websocket(URI.create("ws://localhost:8080/rsocket"));

            // Отправляем запрос по маршруту "greeting"
            requester
                    .route("greeting")
                    .data("RSocket over WebSocket")
                    .retrieveMono(String.class)
                    .doOnTerminate(latch::countDown) // Освобождаем блокировку, когда запрос завершен (успешно или с ошибкой)
                    .subscribe(
                            response -> log.info("Got a response: {}", response),  // Логирование успешного ответа
                            error -> {
                                log.error("Error occurred: {}", error.getMessage());  // Логирование ошибки
                                latch.countDown();  // Освобождаем блокировку при ошибке
                            }
                    );

            // Ожидаем завершения асинхронной операции
            latch.await();
        };
    }
}



