package com.sample.resolver.impl;

import static com.sample.domain.ComponentType.DRINK;
import static com.sample.domain.ComponentType.EXTRA;
import static com.sample.domain.ComponentType.SNACK;
import static java.math.BigDecimal.ONE;
import static org.junit.Assert.assertEquals;

import com.sample.domain.Component;
import com.sample.domain.ComponentType;
import com.sample.domain.Order;
import com.sample.resolver.DiscountResolver;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * Unit testing for {@link FreeExtraWithSnackAndDrinkDiscountResolver}
 */
public class FreeExtraWithSnackAndDrinkDiscountResolverTest {

    private final DiscountResolver discountResolver = new FreeExtraWithSnackAndDrinkDiscountResolver();

    @Test
    public void testResolveZeroDiscountsForEmptyOrder() {

        var order = new Order();
        order.setUsername("myUsername");

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveZeroDiscountsForOrderWithDrinksButNoSnacks() {

        var order = createOrder(4, 0, 4);

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveZeroDiscountsForOrderWithSnacksButNoDrinks() {

        var order = createOrder(0, 4, 4);

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveZeroDiscountsForOrderWithDrinksAndSnacksButNoExtras() {

        var order = createOrder(1, 1, 0);

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveOneDiscountForOrderWithOneDrinkAndOneSnackAndManyExtras() {

        var order = createOrder(1, 1, 4);

        var discounts = discountResolver.resolve(order);

        assertEquals(1, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free extra)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    @Test
    public void testResolveOneDiscountForOrderWithDrinksAndSnacksAndOneExtra() {

        var order = createOrder(2, 3, 1);

        var discounts = discountResolver.resolve(order);

        assertEquals(1, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free extra)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    @Test
    public void testResolveTwoDiscountForOrderWithDrinksAndSnacksAndTwoExtras() {

        var order = createOrder(4, 3, 2);

        var discounts = discountResolver.resolve(order);

        assertEquals(2, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free extra)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    //
    // private

    private Order createOrder(int numDrinks, int numSnacks, int numExtras) {

        var order = new Order();
        order.setUsername("myUsername");

        IntStream.range(0, numDrinks).forEach(index ->
            order.getItems().add(component(DRINK))
        );

        IntStream.range(0, numSnacks).forEach(index ->
            order.getItems().add(component(SNACK))
        );

        IntStream.range(0, numExtras).forEach(index ->
            order.getItems().add(component(EXTRA))
        );

        return order;
    }

    private Component component(ComponentType type) {

        var component = new Component();

        component.setName("myComponent");
        component.setType(type);
        component.setPrice(ONE);

        return component;
    }


}
