package pt.galina.r2dbcpersistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pt.galina.r2dbcpersistence.config")
public class R2dbcPersistenceApplication {


    public static void main(String[] args) {
        SpringApplication.run(R2dbcPersistenceApplication.class, args);
    }


}
