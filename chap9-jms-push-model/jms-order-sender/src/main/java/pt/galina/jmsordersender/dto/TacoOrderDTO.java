package pt.galina.jmsordersender.dto;

import lombok.Data;
import pt.galina.jmsordersender.dto.TacoDTO;

import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO {
    private Date placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;
}
