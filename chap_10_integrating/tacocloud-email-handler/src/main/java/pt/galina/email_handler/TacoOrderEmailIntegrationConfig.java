package pt.galina.email_handler;

import jakarta.mail.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.dsl.Mail;

import java.util.Properties;
@Configuration
public class TacoOrderEmailIntegrationConfig {

    private final Logger log = LoggerFactory.getLogger(TacoOrderEmailIntegrationConfig.class);

    private final EmailProperties emailProperties;

    public TacoOrderEmailIntegrationConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public IntegrationFlow tacoOrderEmailFlow(ImapMailReceiver imapMailReceiver,
                                              EmailToOrderTransformer  emailToOrderTransformer,
                                              OrderSubmitMessageHandler  orderSubmitHandler) {
        log.info("IntegrationFlow:  Getting password authentication {}", emailProperties.getPassword());
        return IntegrationFlow
                .from(Mail.imapInboundAdapter(imapMailReceiver),
                        e -> e
                                .poller( Pollers.fixedDelay(emailProperties.getPollRate()))
                )
                .handle((GenericHandler<Message>) (message, headers) -> {

                    log.info("Received message: {}", message);
                    return null;
                })
                .transform(emailToOrderTransformer)
                .handle(orderSubmitHandler)
                .get();
    }

    @Bean
    public ImapMailReceiver imapMailReceiver() {
        String url = emailProperties.getImapUrl();
        ImapMailReceiver receiver = new ImapMailReceiver(url);
        Properties javaMailProperties = getJavaMailProperties();

        receiver.setJavaMailProperties(javaMailProperties);
        receiver.setShouldMarkMessagesAsRead(true);
        receiver.setShouldDeleteMessages(false);
        log.info("imapMailReceiver():  Getting password authentication {}", emailProperties.getPassword());

        // No need to create a new session here

        return receiver;
    }


    private Properties getJavaMailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.imaps.ssl.enable", "true");
        javaMailProperties.setProperty("mail.imaps.ssl.trust", "*");
        javaMailProperties.setProperty("mail.imaps.socketFactory.fallback", "false");
        javaMailProperties.setProperty("mail.store.protocol", "imaps");
        javaMailProperties.setProperty("mail.imaps.auth", "true");
        javaMailProperties.setProperty("mail.debug", "true");
        javaMailProperties.setProperty("mail.imaps.port", String.valueOf(emailProperties.getPort()));
        javaMailProperties.setProperty("mail.imaps.host", emailProperties.getHost());
        javaMailProperties.setProperty("mail.imaps.username", emailProperties.getUsername());
        javaMailProperties.setProperty("mail.imaps.password", emailProperties.getPassword());
        return javaMailProperties;
    }
}
