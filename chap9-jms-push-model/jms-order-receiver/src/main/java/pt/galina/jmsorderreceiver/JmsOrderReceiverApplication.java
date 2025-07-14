package pt.galina.jmsorderreceiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsOrderReceiverApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsOrderReceiverApplication.class, args);
    }

}
