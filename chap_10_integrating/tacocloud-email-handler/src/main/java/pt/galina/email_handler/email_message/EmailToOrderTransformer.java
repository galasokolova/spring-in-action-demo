package pt.galina.email_handler.email_message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pt.galina.email_handler.taco.EmailOrder;
import pt.galina.email_handler.taco.Ingredient;
import pt.galina.email_handler.taco.Taco;


@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private final Logger log = LoggerFactory.getLogger(EmailToOrderTransformer.class);
    private static final String SUBJECT_KEYWORDS = "TACO ORDER";

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) {
        EmailOrder tacoOrder = processPayload(mailMessage);
        if (tacoOrder == null) {
            throw new IllegalStateException("Failed to process the email into an order.");
        }
        log.debug("⏩Successfully transformed the email message into an order");
        return MessageBuilder.withPayload(tacoOrder);
    }

    private EmailOrder processPayload(Message mailMessage) {
        try {
            Folder folder = mailMessage.getFolder();
            if (folder != null && !folder.isOpen()) {
                folder.open(Folder.READ_WRITE);
            }

            EmailOrder order = getOrder(mailMessage, folder);
            if (order != null) return order;
        } catch (MessagingException | IOException e) {
            log.error("⏩Error in processPayload: {}", e.getMessage(), e);
        }

        return null;
    }

    private EmailOrder getOrder(Message mailMessage, Folder folder) throws MessagingException, IOException {

        MimeMessage cachedMessage = new MimeMessage((MimeMessage) mailMessage);


        String subject = cachedMessage.getSubject(); // Используем cachedMessage
        if (subject != null && subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
            String email = ((InternetAddress) cachedMessage.getFrom()[0]).getAddress();
            String content = EmailContentExtractor.getTextFromMessage(cachedMessage);

            if (content != null && !content.isEmpty()) {
                EmailOrder order = parseEmailToOrder(email, content);
                log.debug("⏩Parsed Email Order: {}", order);

                if (folder != null && folder.isOpen()) {
                    try {
                        log.info("⏩Closing folder");
                        folder.close(false);
                    } catch (MessagingException e) {
                        log.error("⏩Error closing folder: {}", e.getMessage(), e);
                    }
                }

                return order;
            }
        }
        return null;
    }


    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (!line.trim().isEmpty() && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(", ");

                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    }
                }

                // Проверка на дубликаты тако
                Taco existingTaco = order.findTacoByName(tacoName);
                if (existingTaco != null) {
                    existingTaco.getIngredientCodes().addAll(ingredientCodes);
                } else {
                    Taco taco = new Taco(tacoName);
                    taco.setIngredientCodes(ingredientCodes);
                    order.addTaco(taco);
                }
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        String ucIngredientName = ingredientName.toUpperCase();
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ingredientNameUpper = ingredient.getName().toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucIngredientName, ingredientNameUpper) < 3
                    || ucIngredientName.contains(ingredientNameUpper)
                    || ingredientNameUpper.contains(ucIngredientName)) {
                log.debug("⏩⏩⏩Parsed Email Order : {}", ingredient.getId());
                return ingredient.getId();
            }
        }
        return null;
    }


    private static final Ingredient[] ALL_INGREDIENTS = {
            new Ingredient("FLTO", "FLOUR TORTILLA"),
            new Ingredient("COTO", "CORN TORTILLA"),
            new Ingredient("GRBF", "GROUND BEEF"),
            new Ingredient("CARN", "CARNITAS"),
            new Ingredient("TMTO", "TOMATOES"),
            new Ingredient("LETC", "LETTUCE"),
            new Ingredient("CHED", "CHEDDAR"),
            new Ingredient("JACK", "MONTERREY JACK"),
            new Ingredient("SLSA", "SALSA"),
            new Ingredient("SRCR", "SOUR CREAM")
    };
}
