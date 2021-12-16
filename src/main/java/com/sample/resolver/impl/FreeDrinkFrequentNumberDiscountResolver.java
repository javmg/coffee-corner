package com.sample.resolver.impl;

import static com.sample.domain.ComponentType.DRINK;

import com.sample.domain.Order;
import com.sample.domain.OrderDiscount;
import com.sample.repository.OrderRepository;
import com.sample.resolver.DiscountResolver;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FreeDrinkFrequentNumberDiscountResolver implements DiscountResolver {

    private final static int DEFAULT_FREQUENCY_NUMBER = 5;

    private final int frequencyNumber;

    private final OrderRepository orderRepository;

    public FreeDrinkFrequentNumberDiscountResolver(OrderRepository orderRepository) {
        this(DEFAULT_FREQUENCY_NUMBER, orderRepository);
    }

    public FreeDrinkFrequentNumberDiscountResolver(
        int frequencyNumber,
        OrderRepository orderRepository
    ) {
        this.frequencyNumber = frequencyNumber;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDiscount> resolve(Order order) {

        var previousOrders = orderRepository.getByUser(order.getUsername());

        // count number of previous drinks

        var numPreviousDrinks = previousOrders.stream()
            .map(Order::getItems)
            .flatMap(Collection::stream)
            .filter(item -> item.getType() == DRINK)
            .count();

        // get the remaining number to get to the next x-th item

        var numPreviousDrinksModN = numPreviousDrinks % frequencyNumber;

        // get the drink items in the current order

        var drinkItems = order.getItems().stream()
            .filter(item -> item.getType() == DRINK)
            .collect(Collectors.toList());

        // calculate how many drinks should be discounted

        var numDiscountedDrinks = (numPreviousDrinksModN + drinkItems.size()) / frequencyNumber;

        if (numDiscountedDrinks == 0) {
            return Collections.emptyList();
        }

        return drinkItems.stream()

            .map(item -> {

                var discount = new OrderDiscount();
                discount.setName(item.getName() + String.format(" (Free %sth drink)", frequencyNumber));
                discount.setPrice(item.getPrice().negate());

                return discount;
            })

            .sorted(Comparator.comparing(OrderDiscount::getPrice))

            .limit(numDiscountedDrinks)

            .collect(Collectors.toList());
    }

}
