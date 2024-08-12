//package pt.galina.kafkalistener.message;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.context.TestPropertySource;
//import pt.galina.kafkalistener.dto.TacoDTO;
//import pt.galina.kafkalistener.dto.TacoOrderDTO;
//
//import java.util.Collections;
//import java.util.Date;
//
//import static org.mockito.Mockito.verify;
//
//@SpringBootTest
//@TestPropertySource(properties = {
//        "spring.kafka.bootstrap-servers=${MOCK_KAFKA_URL:localhost:9092}"
//})
//public class OrderListenerTest {
//
//    @MockBean
//    private KitchenUI kitchenUI;
//
//    @Autowired
//    private OrderListener orderListener;
//
//    @Test
//    public void testHandle() {
//        // Arrange
//        TacoOrderDTO orderDTO = new TacoOrderDTO();
//        orderDTO.setDeliveryName("John Doe");
//        orderDTO.setPlacedAt(new Date());
//        orderDTO.setTacos(Collections.singletonList(new TacoDTO()));
//
//        // Act
//        orderListener.handle(orderDTO);
//
//        // Assert
//        verify(kitchenUI, Mockito.times(1)).displayOrder(orderDTO);
//    }
//}
