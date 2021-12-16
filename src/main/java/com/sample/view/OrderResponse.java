package com.sample.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class OrderResponse {

    private final String id;

    private final String username;

    private final List<OrderResponsePart> items;

    private final List<OrderResponsePart> discounts;

    private final BigDecimal totalPrice;

    public OrderResponse(
        String id,
        String username,
        List<OrderResponsePart> items,
        List<OrderResponsePart> discounts
    ) {
        this.id = id;
        this.username = username;
        this.items = items;
        this.discounts = discounts;
        this.totalPrice = Stream.concat(items.stream(), discounts.stream())
            .map(OrderResponsePart::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<OrderResponsePart> getItems() {
        return items;
    }

    public List<OrderResponsePart> getDiscounts() {
        return discounts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public static class OrderResponsePart {

        private final String name;

        private final BigDecimal price;

        public OrderResponsePart(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
