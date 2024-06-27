package pt.galina.spring_webflux_demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDTO{

    private String name;
    private List<String> ingredients;

}
