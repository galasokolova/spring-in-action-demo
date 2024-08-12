package pt.galina.kafkalistener.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.Model;
import pt.galina.kafkalistener.dto.TacoOrderDTO;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "your-topic" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@ActiveProfiles("test")  // Ensure that the test profile is active
class OrderReceiverControllerTest {

    private OrderReceiverController controller;
    private KitchenUI kitchenUI;
    private Model model;

    @BeforeEach
    void setUp() {
        kitchenUI = Mockito.mock(KitchenUI.class);
        model = Mockito.mock(Model.class);
        controller = new OrderReceiverController(kitchenUI);
    }

    @Test
    void showAllReceivedOrders_whenOrdersExist() {
        // Given
        List<TacoOrderDTO> orders = List.of(new TacoOrderDTO());
        when(kitchenUI.getAllOrders()).thenReturn(orders);

        // When
        String viewName = controller.showAllReceivedOrders(model);

        // Then
        assertEquals("receivedOrder", viewName);
        verify(model).addAttribute("orders", orders);
        verifyNoMoreInteractions(model);
    }

    @Test
    void showAllReceivedOrders_whenNoOrdersExist() {
        // Given
        when(kitchenUI.getAllOrders()).thenReturn(Collections.emptyList());

        // When
        String viewName = controller.showAllReceivedOrders(model);

        // Then
        assertEquals("noOrderFound", viewName);
        verify(model, never()).addAttribute(anyString(), any());
    }
}
