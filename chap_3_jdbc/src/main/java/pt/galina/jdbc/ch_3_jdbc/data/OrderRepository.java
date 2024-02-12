package pt.galina.jdbc.ch_3_jdbc.data;

import pt.galina.jdbc.ch_3_jdbc.entity.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
