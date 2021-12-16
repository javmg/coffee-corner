package com.sample.repository;

import com.sample.domain.Order;
import java.util.List;

public interface OrderRepository {

    List<Order> getByUser(String username);

    Order save(Order order);
}
