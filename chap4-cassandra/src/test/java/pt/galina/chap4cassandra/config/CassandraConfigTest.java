package pt.galina.chap4cassandra.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.core.CassandraTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataCassandraTest
@Import(CassandraConfig.class)
class CassandraConfigTest {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Test
    void cassandraTemplateIsNotNull() {
        assertThat(cassandraTemplate).isNotNull();
    }
}
