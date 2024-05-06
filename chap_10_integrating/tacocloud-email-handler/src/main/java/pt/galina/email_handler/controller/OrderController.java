//package pt.galina.email_handler.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import pt.galina.email_handler.taco.EmailOrder;
//
//@RestController
//public class OrderController {
//    @PostMapping("/orders/fromEmail")
//    public ResponseEntity<String> receiveOrder(@RequestBody EmailOrder order) {
//        System.out.println("Order received: " + order);
//        return ResponseEntity.ok("Order processed");
//    }
//}
