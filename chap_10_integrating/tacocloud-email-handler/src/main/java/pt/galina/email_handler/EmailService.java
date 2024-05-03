package pt.galina.email_handler;

import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

@Service
public class EmailService {

    private final EmailProperties emailProperties;

    public EmailService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
        System.out.println("Using host: " + emailProperties.getHost());
        System.out.println("Using username: " + emailProperties.getUsername());
        // так далее для других свойств
    }

    public boolean testConnection() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", emailProperties.getHost());
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.timeout", "10000");

        Session session = Session.getDefaultInstance(props);
        Store store = null;
        Folder inbox = null;

        try {
            store = session.getStore();
            store.connect(emailProperties.getUsername(), emailProperties.getPassword());
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages:- " + messageCount);

            return true;  // Успешное подключение
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;  // Не удалось подключиться
        } finally {
            try {
                if (inbox != null) {
                    inbox.close(false);
                }
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
