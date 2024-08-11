package pt.galina.orderreceiver.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class TacoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private List<String> ingredients;

}

