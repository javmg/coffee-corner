package com.sample.domain;

import java.math.BigDecimal;

/**
 * This class contains the components contained in the products.
 */
public class Component {

    private ComponentId id;

    private String name;

    private ComponentType type;

    private BigDecimal price;

    public Component() {
    }

    public Component(ComponentId id, String name, ComponentType type, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public ComponentId getId() {
        return id;
    }

    public void setId(ComponentId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getType() {
        return type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
