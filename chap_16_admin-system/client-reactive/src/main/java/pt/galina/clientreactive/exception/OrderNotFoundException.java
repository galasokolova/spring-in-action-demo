package pt.galina.chap_15_actuatordemo.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Order with ID " + orderId + " not found");
    }
}

