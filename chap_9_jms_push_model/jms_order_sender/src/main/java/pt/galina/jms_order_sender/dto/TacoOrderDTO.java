package pt.galina.jms_order_sender.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO {
    private Date placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;
}
