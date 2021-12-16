package com.sample.view;

import java.util.List;

public class OrderRequest {

    private final String username;

    private final List<Product> products;

    public OrderRequest(String username, List<Product> products) {
        this.username = username;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getUsername() {
        return username;
    }
}
