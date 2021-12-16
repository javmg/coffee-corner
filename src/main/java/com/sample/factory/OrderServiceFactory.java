package com.sample.factory;

import static com.sample.domain.ComponentId.BACON_ROLL;
import static com.sample.domain.ComponentId.COFFEE_EXTRA_MILK;
import static com.sample.domain.ComponentId.COFFEE_FOAMED_MILK;
import static com.sample.domain.ComponentId.COFFEE_LARGE;
import static com.sample.domain.ComponentId.COFFEE_MEDIUM;
import static com.sample.domain.ComponentId.COFFEE_SMALL;
import static com.sample.domain.ComponentId.COFFEE_SPECIAL_ROAST_COFFEE;
import static com.sample.domain.ComponentId.FRESHLY_SQUEEZED_ORANGE_JUICE;
import static com.sample.domain.ComponentType.DRINK;
import static com.sample.domain.ComponentType.EXTRA;
import static com.sample.domain.ComponentType.SNACK;

import com.sample.domain.Component;
import com.sample.repository.impl.ComponentRepositoryImpl;
import com.sample.repository.impl.OrderRepositoryImpl;
import com.sample.resolver.impl.FreeDrinkFrequentNumberDiscountResolver;
import com.sample.resolver.impl.FreeExtraWithSnackAndDrinkDiscountResolver;
import com.sample.service.OrderService;
import com.sample.service.impl.OrderServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderServiceFactory {

    public static OrderService createOrderService() {

        var orderRepository = new OrderRepositoryImpl();

        var mapIdAndComponent = Stream.of(

            new Component(COFFEE_SMALL, "Coffee small", DRINK, BigDecimal.valueOf(2.5)),
            new Component(COFFEE_MEDIUM, "Coffee medium", DRINK, BigDecimal.valueOf(3.0)),
            new Component(COFFEE_LARGE, "Coffee large", DRINK, BigDecimal.valueOf(3.5)),

            new Component(COFFEE_EXTRA_MILK, "Extra milk", EXTRA, BigDecimal.valueOf(0.3)),
            new Component(COFFEE_FOAMED_MILK, "Foamed milk", EXTRA, BigDecimal.valueOf(0.5)),
            new Component(COFFEE_SPECIAL_ROAST_COFFEE, "Special roast coffee", EXTRA, BigDecimal.valueOf(0.9)),

            new Component(BACON_ROLL, "Bacon Roll", SNACK, BigDecimal.valueOf(4.5)),
            new Component(FRESHLY_SQUEEZED_ORANGE_JUICE, "Freshly squeezed orange juice", DRINK, BigDecimal.valueOf(3.95))

        ).collect(Collectors.toMap(Component::getId, Function.identity()));

        var componentRepository = new ComponentRepositoryImpl(mapIdAndComponent);

        var discountResolvers = List.of(
            new FreeDrinkFrequentNumberDiscountResolver(orderRepository),
            new FreeExtraWithSnackAndDrinkDiscountResolver()
        );

        return new OrderServiceImpl(orderRepository, discountResolvers, componentRepository);
    }

}
