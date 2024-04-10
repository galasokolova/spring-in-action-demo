package pt.galina.message_sender.dto;

import lombok.Data;
import pt.galina.message_sender.dto.TacoDTO;

import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO {
    private Date placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;
}
