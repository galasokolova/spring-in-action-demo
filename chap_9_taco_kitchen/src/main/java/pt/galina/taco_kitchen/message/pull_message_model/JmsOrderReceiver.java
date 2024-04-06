//package pt.galina.taco_kitchen.pull_message_model;
//
//
//import jakarta.jms.JMSException;
//import jakarta.jms.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.stereotype.Component;
//import pt.galina.taco_kitchen.entity.taco.TacoOrder;
//
//@Component
//public class JmsOrderReceiver implements OrderReceiver{
//
//    private final JmsTemplate jms;
//    private final MessageConverter converter;
//
//    @Autowired
//    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
//        this.jms = jms;
//        this.converter = converter;
//    }
//
////    @Override
////    public TacoOrder receiveOrder() throws JMSException {
////
////        Message message = jms.receive("tacocloud.order.queue");
////        if (message != null){
////            return (TacoOrder) converter.fromMessage(message);
////        }else {
////            throw new JMSException("No message received from 'tacocloud.order.queue'");
////        }
////    }
//
//    @Override
//    public TacoOrder receiveAndConvertOrder() {
//        return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
//    }
//}
