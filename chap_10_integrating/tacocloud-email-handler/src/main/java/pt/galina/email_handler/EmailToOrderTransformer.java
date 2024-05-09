package pt.galina.email_handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
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
    private final EmailProperties emailProperties;

    public EmailToOrderTransformer(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
        log.info("EmailToOrderTransformer created {} ", emailProperties);
    }

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder> doTransform(Message mailMessage) {
        EmailOrder tacoOrder = processPayload(mailMessage);
        return MessageBuilder.withPayload(tacoOrder);
    }

    private EmailOrder processPayload(Message mailMessage) {


        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {
                String email = ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException e) {
            log.error("❗processPayload ❗❗MessagingException: {}", e);
        } catch (IOException e) {
            log.error("IOException: {}", e);
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
                Taco taco = new Taco(tacoName);
                taco.setIngredientCodes(ingredientCodes);
                order.addTaco(taco);
            }
        }
        log.info("Parsed Email Order: {}", order);
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        String ucIngredientName = ingredientName.toUpperCase();
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ingredientNameUpper = ingredient.getName().toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucIngredientName, ingredientNameUpper) < 3
                    || ucIngredientName.contains(ingredientNameUpper)
                    || ingredientNameUpper.contains(ucIngredientName)) {
                log.info("Parsed Email Order : {}", ingredient.getId());
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
