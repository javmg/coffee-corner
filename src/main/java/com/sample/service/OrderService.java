package com.sample.service;

import com.sample.view.OrderRequest;
import com.sample.view.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest criteria);

}
