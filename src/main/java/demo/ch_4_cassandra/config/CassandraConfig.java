package demo.ch_4_cassandra.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "demo.ch_4_cassandra")
public class CassandraConfig {

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession cqlSession) {
        return new CassandraTemplate(cqlSession);
    }
}
