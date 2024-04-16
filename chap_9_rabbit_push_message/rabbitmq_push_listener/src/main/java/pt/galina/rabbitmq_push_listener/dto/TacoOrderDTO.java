package pt.galina.rabbitmq_push_listener.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
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
