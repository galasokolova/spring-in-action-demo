package pt.galina.kafka_producer.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDTO {

    private String name;
    private List<String> ingredients;

}
