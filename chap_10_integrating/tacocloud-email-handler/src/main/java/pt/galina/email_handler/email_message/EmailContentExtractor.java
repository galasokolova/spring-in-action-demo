package pt.galina.email_handler.email_message;

import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EmailContentExtractor {

    private static final Logger log = LoggerFactory.getLogger(EmailContentExtractor.class);

    public static String getTextFromMessage(Message message) throws IOException, MessagingException {
        log.debug("⏩⏩⏩Extracting text from message with subject: {}", message.getSubject());
        if (message.isMimeType("text/plain")) {
            log.debug("⏩⏩⏩Message is of type text/plain");
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            log.debug("⏩⏩⏩Message is of type multipart/*");
            return getTextFromMimeMultipart((MimeMultipart) message.getContent());

        }
        log.warn("⏩⏩⏩Message is of unknown type");
        return null;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        log.debug("⏩⏩⏩MimeMultipart has {} parts", count);
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            log.debug("⏩⏩⏩Processing part {} of type {}", i, bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
                log.debug("⏩⏩⏩Part is of type text/plain");
                result.append(bodyPart.getContent().toString());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                log.debug("⏩⏩⏩Part is of type MimeMultipart");
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            } else if (bodyPart.isMimeType("text/html")) {
                log.debug("⏩⏩⏩Part is of type text/html");
                String html = (String) bodyPart.getContent();
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("body");
                for (Element element : elements) {
                    result.append(element.text());
                }
            }
        }
        return result.toString();
    }
}


