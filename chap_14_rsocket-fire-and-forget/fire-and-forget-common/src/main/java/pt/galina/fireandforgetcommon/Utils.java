package pt.galina.fireandforgetcommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);  // Задержка 5 секунд
        } catch (InterruptedException e) {
            log.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
