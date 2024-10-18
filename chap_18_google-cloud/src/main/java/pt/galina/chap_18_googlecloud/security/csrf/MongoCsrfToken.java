package pt.galina.chap_18_googlecloud.security.csrf;

import lombok.Data;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "csrf_tokens")
@Data
public class MongoCsrfToken implements CsrfToken {

    @Id
    private String sessionId;
    private String token;

    private static final String HEADER_NAME = "X-CSRF-TOKEN";
    private static final String PARAMETER_NAME = "_csrf";

    public MongoCsrfToken(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getParameterName() {
        return PARAMETER_NAME;
    }

    @Override
    public String getHeaderName() {
        return HEADER_NAME;
    }
}


