package pt.galina.email_handler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tacocloud.email")
@Slf4j  // Аннотация Lombok для логирования
public class EmailProperties {

    private String username;
    private String password;
    private String host;
    private int port;
    private String mailbox;
    private long pollRate = 30000;

    public String getImapUrl() {
//        String safePassword = password.replace(" ", "%20");  // Заменяем пробелы на %20
        String url = String.format("imaps://%s:%s@%s/%s", username, password, host, mailbox);
        log.info("{}Generated IMAP URL: {}", "❗ ".repeat(3), url);  // Логирование сформированного URL
        return url;
    }
}
