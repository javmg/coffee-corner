package com.sample.resolver.impl;

import static com.sample.domain.ComponentType.DRINK;
import static com.sample.domain.ComponentType.EXTRA;
import static com.sample.domain.ComponentType.SNACK;
import static java.util.Collections.emptyList;

import com.sample.domain.Component;
import com.sample.domain.Order;
import com.sample.domain.OrderDiscount;
import com.sample.resolver.DiscountResolver;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FreeExtraWithSnackAndDrinkDiscountResolver implements DiscountResolver {

    @Override
    public List<OrderDiscount> resolve(Order order) {

        //
        // group by type

        var mapTypeAndItems = order.getItems().stream()
            .collect(Collectors.groupingBy(Component::getType));

        var drinks = mapTypeAndItems.getOrDefault(DRINK, emptyList());

        var snacks = mapTypeAndItems.getOrDefault(SNACK, emptyList());

        int numDiscounts = Math.min(drinks.size(), snacks.size());

        if (numDiscounts == 0) {
            return emptyList();
        }

        var extras = mapTypeAndItems.getOrDefault(EXTRA, emptyList());

        return extras.stream()

            .map(item -> {

                var discount = new OrderDiscount();
                discount.setName(item.getName() + " (Free extra)");
                discount.setPrice(item.getPrice().negate());

                return discount;
            })

            .sorted(Comparator.comparing(OrderDiscount::getPrice))

            .limit(Math.min(numDiscounts, extras.size()))

            .collect(Collectors.toList());
    }

}
