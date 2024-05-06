package pt.galina.email_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.dsl.Mail;

import java.util.Properties;

@Configuration
public class TacoOrderEmailIntegrationConfig {

    private static final Logger log = LoggerFactory.getLogger(TacoOrderEmailIntegrationConfig.class);


    private final EmailProperties emailProperties;

    @Autowired
    public TacoOrderEmailIntegrationConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;

        System.out.println("✅ ✅ ✅ Using host: " + emailProperties.getHost());
        System.out.println("✅ ✅ ✅Using username: " + emailProperties.getUsername());
        System.out.println("✅ ✅ ✅Password: " + emailProperties.getPassword());
    }

    @Bean
    public ImapMailReceiver imapMailReceiver() {
        String imapUrl = emailProperties.getImapUrl();  // Убедитесь, что URL формируется правильно
        ImapMailReceiver receiver = new ImapMailReceiver(imapUrl);


        Properties javaMailProperties = new Properties();

        javaMailProperties.put("mail.store.protocol", "imaps");
        javaMailProperties.put("mail.imaps.host", emailProperties.getHost());
        javaMailProperties.put("mail.imaps.port", String.valueOf(emailProperties.getPort()));
        javaMailProperties.put("mail.imaps.password", emailProperties.getPassword());

        log.info(emailProperties.getPassword(), "✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅ ✅");

        javaMailProperties.put("mail.imaps.timeout", "10000");
        javaMailProperties.put("mail.imap.ssl.enable", "true"); // Включение SSL
        javaMailProperties.put("mail.imap.ssl.trust", "*");
        javaMailProperties.put("mail.imap.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.imap.socketFactory.fallback", "false");
        javaMailProperties.put("mail.debug", "true"); // Включение отладки при необходимости
        javaMailProperties.put("mail.imaps.auth", "true");
        javaMailProperties.put("mail.imaps.auth.mechanisms", "XOAUTH2 LOGIN PLAIN");
        javaMailProperties.put("mail.imap.partialfetch", "false");

        // Управление механизмами аутентификации
        javaMailProperties.put("mail.imap.auth.login.disable", "false"); // false, если нужно разрешить LOGIN
        javaMailProperties.put("mail.imap.auth.plain.disable", "false"); // false, если нужно разрешить PLAIN
        javaMailProperties.put("mail.imaps.auth.ntlm.disable", "true");  // true, если нужно отключить NTLM

        javaMailProperties.forEach((key, value) -> System.out.println("❗ TacoOrderEmailIntegrationConfig ❗" + key + ": " + value));

        receiver.setJavaMailProperties(javaMailProperties);
        receiver.setShouldMarkMessagesAsRead(true); // или false в зависимости от вашего случая
        receiver.setShouldDeleteMessages(false); // обычно false для большинства случаев

        receiver.setJavaMailProperties(javaMailProperties);
        receiver.setShouldMarkMessagesAsRead(true); // или false в зависимости от вашего случая
        receiver.setShouldDeleteMessages(false); // обычно false для большинства случаев

        return receiver;
    }


    @Bean
    public IntegrationFlow tacoOrderEmailFlow(
            EmailToOrderTransformer emailToOrderTransformer,
            OrderSubmitMessageHandler orderSubmitMessageHandler) {

        System.out.println("❗ ".repeat(10) +"Connecting with properties: " + emailProperties);
        System.out.println("❗ ".repeat(10) +"Connecting with emailToOrderTransformer: " + emailToOrderTransformer.toString());
        System.out.println("❗ ".repeat(10) +"Connecting with orderSubmitMessageHandler: " + orderSubmitMessageHandler.toString());


        return IntegrationFlow
                .from(Mail.imapInboundAdapter(imapMailReceiver()),
                        e -> e.poller(Pollers.fixedDelay(emailProperties.getPollRate())))
                .transform(emailToOrderTransformer)
                .handle(orderSubmitMessageHandler)
                .get();
    }
}
