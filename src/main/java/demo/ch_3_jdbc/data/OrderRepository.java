package demo.ch_3_jdbc.data;

import demo.ch_3_jdbc.entity.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
