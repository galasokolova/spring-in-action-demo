package pt.galina.email_handler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TacocloudEmailHandlerApplication {

    public static void main(String[] args) {

        SpringApplication.run(TacocloudEmailHandlerApplication.class, args);

    }
    @Bean
    public CommandLineRunner testEmailConnection(EmailService emailService) {
        return args -> {
            boolean isConnected = emailService.testConnection();
            System.out.println("Connection successful: " + isConnected);
        };
    }

}
