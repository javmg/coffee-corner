package com.sample.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the customers' orders
 */
public class Order {

    private String id;

    private String username;

    private List<Component> items = new ArrayList<>();

    private List<OrderDiscount> discounts = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Component> getItems() {
        return items;
    }

    public void setItems(List<Component> items) {
        this.items = items;
    }

    public List<OrderDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<OrderDiscount> discounts) {
        this.discounts = discounts;
    }

}
