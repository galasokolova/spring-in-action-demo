package pt.galina.chap_18_googlecloud.security.csrf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "csrf_tokens")
public class MongoCsrfToken {

    @Id
    private String sessionId;
    private String token;

    public MongoCsrfToken(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }

}
