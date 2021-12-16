package com.sample.resolver;

import com.sample.domain.Order;
import com.sample.domain.OrderDiscount;
import java.util.List;

public interface DiscountResolver {

    List<OrderDiscount> resolve(Order order);
}
