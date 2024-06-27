package pt.galina.spring_webflux_demo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO{
    private Date placedAt;
    private String deliveryName;
    private List<TacoDTO> tacos;

}
