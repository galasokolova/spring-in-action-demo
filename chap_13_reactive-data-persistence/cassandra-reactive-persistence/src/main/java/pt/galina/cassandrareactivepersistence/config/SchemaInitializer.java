package pt.galina.cassandrareactivepersistence.config;

import com.datastax.oss.driver.api.core.CqlSession;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class SchemaInitializer {

    private final CqlSession session;

    @Autowired
    public SchemaInitializer(CqlSession session) {
        this.session = session;
    }

    @PostConstruct
    public void initialize() {
        executeCqlScript();
    }

    private void executeCqlScript() {
        try {
            Resource resource = new ClassPathResource("schema.cql");
            String cql = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            CqlTemplate cqlTemplate = new CqlTemplate(session);
            cqlTemplate.execute(cql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute CQL script: " + "schema.cql", e);
        }
    }
}

