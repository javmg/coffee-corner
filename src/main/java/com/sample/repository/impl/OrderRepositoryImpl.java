package com.sample.repository.impl;

import com.sample.domain.Order;
import com.sample.repository.OrderRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is a dummy order repository using a map as a backing structure.
 */
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<String, List<Order>> mapUsernameAndOrders = new HashMap<>();

    @Override
    public List<Order> getByUser(String username) {
        return mapUsernameAndOrders.getOrDefault(username, Collections.emptyList());
    }

    @Override
    public Order save(Order order) {

        var username = order.getUsername();

        order.setId(UUID.randomUUID().toString());

        order.getDiscounts().forEach(orderDiscount ->
            orderDiscount.setId(UUID.randomUUID().toString())
        );

        mapUsernameAndOrders.merge(username, new ArrayList<>(List.of(order)), (a, b) -> {
            a.addAll(b);
            return a;
        });

        return order;
    }
}
