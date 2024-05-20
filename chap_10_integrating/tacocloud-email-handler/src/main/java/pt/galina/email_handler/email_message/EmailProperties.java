package pt.galina.email_handler.email_message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "tacocloud.email")
@Slf4j
public class EmailProperties {

    public EmailProperties() {
        log.info("Creating EmailProperties bean");
    }

    private String username;
    private String password;
    private String host;
    private int port;
    private String mailbox;
    private long pollRate = 30000;

    public String getImapUrl() {
        String safePassword = password.replace(" ", "%20");
        String url = String.format("imaps://%s:%s@%s/%s", this.username, safePassword, this.host, this.mailbox);
        log.info("{}Generated IMAP URL: {}", "❗ ".repeat(3), url);
        return url;
    }
}
