package pt.galina.kafka_producer.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Date placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;
}