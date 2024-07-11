package pt.galina.chap3jdbc.data;

import pt.galina.chap3jdbc.entity.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
