package pt.galina.chap_18_googlecloud.security.csrf;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "csrf_tokens")
public class MongoCsrfToken {

    @Id
    private String sessionId;
    private String token;

    public MongoCsrfToken(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }

    // Геттеры и сеттеры
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
