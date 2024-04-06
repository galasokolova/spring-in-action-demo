package pt.galina.jms_push_listener.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDTO {
    private String name;
    private List<String> ingredients;

}
