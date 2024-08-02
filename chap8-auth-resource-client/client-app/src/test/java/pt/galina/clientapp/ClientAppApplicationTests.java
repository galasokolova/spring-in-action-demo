package pt.galina.clientapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@SpringBootTest(classes = ClientAppApplication.class)
class ClientAppApplicationTests {

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private ClientAppApplication clientAppApplication;

    @Test
    void contextLoads() {

    }
}
