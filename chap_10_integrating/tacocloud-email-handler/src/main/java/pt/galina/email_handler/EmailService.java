package pt.galina.email_handler;

import jakarta.mail.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

@Service
public class EmailService {

    private final EmailProperties emailProperties;

    public EmailService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
        System.out.println("Using host: " + emailProperties.getHost());
        System.out.println("Using username: " + emailProperties.getUsername());
        System.out.println("Password: " + emailProperties.getPassword());  // Убедитесь, что пароль выводится правильно

        // так далее для других свойств
    }

    public boolean testConnection() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", emailProperties.getHost());
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.timeout", "30000");

        Session session = Session.getDefaultInstance(props);
        Store store = null;
        Folder inbox = null;

        try {
            store = session.getStore();
            store.connect(emailProperties.getUsername(), emailProperties.getPassword());
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages: " + messageCount);

            // Получаем сообщения из почтового ящика
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                try {
                    System.out.println("Email Number: " + message.getMessageNumber());
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + Arrays.toString(message.getFrom()));
                    System.out.println("❗ ".repeat(5) +"Connecting with properties: " + emailProperties);

                    System.out.println("Text: " + getTextFromMessage(message));
                } catch (Exception e) {
                    System.out.println("Error reading content of the message.");
                    e.printStackTrace();
                }
            }

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

 //    Метод для извлечения текста сообщения
    static String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    return bodyPart.getContent().toString();
                }
            }
        }
        return "";
    }
}
