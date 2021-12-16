package com.sample.view;

import static com.sample.domain.ComponentId.COFFEE_EXTRA_MILK;
import static com.sample.domain.ComponentId.COFFEE_FOAMED_MILK;
import static com.sample.domain.ComponentId.COFFEE_LARGE;
import static com.sample.domain.ComponentId.COFFEE_MEDIUM;
import static com.sample.domain.ComponentId.COFFEE_SMALL;
import static com.sample.domain.ComponentId.COFFEE_SPECIAL_ROAST_COFFEE;

import com.sample.domain.ComponentId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductCoffee implements Product {

    private final CoffeeSizeType size;

    private final Set<CoffeeExtraType> extras;

    public enum CoffeeSizeType {
        SMALL(COFFEE_SMALL),
        MEDIUM(COFFEE_MEDIUM),
        LARGE(COFFEE_LARGE),
        ;

        CoffeeSizeType(ComponentId componentId) {
            this.componentId = componentId;
        }

        private final ComponentId componentId;
    }

    public enum CoffeeExtraType {
        EXTRA_MILK(COFFEE_EXTRA_MILK),
        FOAMED_MILK(COFFEE_FOAMED_MILK),
        SPECIAL_ROAST_COFFEE(COFFEE_SPECIAL_ROAST_COFFEE),
        ;

        CoffeeExtraType(ComponentId componentId) {
            this.id = componentId;
        }

        private final ComponentId id;
    }

    private ProductCoffee(CoffeeSizeType type, Set<CoffeeExtraType> extras) {

        this.size = type;
        this.extras = extras;
    }

    @Override
    public List<ComponentId> getComponentIds() {

        var results = new ArrayList<ComponentId>();

        results.add(size.componentId);

        results.addAll(extras.stream().map(coffeeExtraType -> coffeeExtraType.id)
            .collect(Collectors.toList()));

        return results;
    }

    public CoffeeSizeType getSize() {
        return size;
    }

    public Set<CoffeeExtraType> getExtras() {
        return extras;
    }

    public static class ProductCoffeeBuilder {

        private final CoffeeSizeType size;

        private final Set<CoffeeExtraType> extras = new HashSet<>();

        public ProductCoffeeBuilder(CoffeeSizeType size) {
            this.size = size;
        }

        public ProductCoffeeBuilder addExtra(CoffeeExtraType extra) {
            this.extras.add(extra);

            return this;
        }

        public ProductCoffee build() {
            return new ProductCoffee(size, extras);
        }
    }
}
