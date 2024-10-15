package pt.galina.chap_18_googlecloud.security.csrf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "csrf_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CsrfTokenDocument {

    @Id
    private String sessionId;
    private String token;
    private String headerName;
    private String parameterName;

}

