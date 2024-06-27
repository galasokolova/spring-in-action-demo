package pt.galina.spring_webflux_demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class TacoOrderDTO {
    private String id;
    private String placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;
    private String userId;
}
