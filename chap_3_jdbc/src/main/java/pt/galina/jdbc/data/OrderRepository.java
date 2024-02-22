package pt.galina.jdbc.data;

import pt.galina.jdbc.entity.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
