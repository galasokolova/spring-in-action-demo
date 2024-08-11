package pt.galina.ordersender.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDTO{

    private String name;
    private List<String> ingredients;

}
