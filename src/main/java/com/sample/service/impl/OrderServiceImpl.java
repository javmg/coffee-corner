package com.sample.service.impl;


import com.sample.domain.Order;
import com.sample.repository.ComponentRepository;
import com.sample.repository.OrderRepository;
import com.sample.resolver.DiscountResolver;
import com.sample.service.OrderService;
import com.sample.view.OrderRequest;
import com.sample.view.OrderResponse;
import com.sample.view.OrderResponse.OrderResponsePart;
import com.sample.view.Product;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private final ComponentRepository componentRepository;

    private final List<DiscountResolver> discountResolvers;

    private final OrderRepository orderRepository;

    public OrderServiceImpl(
        OrderRepository orderRepository,
        List<DiscountResolver> discountResolvers,
        ComponentRepository componentRepository
    ) {
        this.orderRepository = orderRepository;
        this.componentRepository = componentRepository;
        this.discountResolvers = discountResolvers;
    }

    @Override
    public OrderResponse createOrder(OrderRequest criteria) {

        var orderItems = criteria.getProducts().stream()

            .map(Product::getComponentIds)

            .flatMap(Collection::stream)

            .map(componentRepository::getById)

            .collect(Collectors.toList());

        var order = new Order();
        order.setUsername(criteria.getUsername());
        order.getItems().addAll(orderItems);

        var orderDiscounts = discountResolvers.stream()
            .map(discountResolver -> discountResolver.resolve(order))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        order.getDiscounts().addAll(orderDiscounts);

        var saved = orderRepository.save(order);

        return toDto(saved);
    }

    //
    // private

    private OrderResponse toDto(Order saved) {

        var username = saved.getUsername();

        var items = saved.getItems().stream()
            .map(orderItem -> new OrderResponsePart(orderItem.getName(), orderItem.getPrice()))
            .collect(Collectors.toList());

        var discounts = saved.getDiscounts().stream()
            .map(discount -> new OrderResponsePart(discount.getName(), discount.getPrice()))
            .collect(Collectors.toList());

        return new OrderResponse(saved.getId(), username, items, discounts);
    }
}
