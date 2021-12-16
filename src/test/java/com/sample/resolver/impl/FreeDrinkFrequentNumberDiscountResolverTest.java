package com.sample.resolver.impl;

import static com.sample.domain.ComponentType.DRINK;
import static com.sample.domain.ComponentType.SNACK;
import static java.math.BigDecimal.ONE;
import static org.junit.Assert.assertEquals;

import com.sample.domain.Component;
import com.sample.domain.ComponentType;
import com.sample.domain.Order;
import com.sample.repository.OrderRepository;
import com.sample.repository.impl.OrderRepositoryImpl;
import com.sample.resolver.DiscountResolver;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing for {@link FreeDrinkFrequentNumberDiscountResolver}
 */
public class FreeDrinkFrequentNumberDiscountResolverTest {

    private OrderRepository orderRepository;

    private DiscountResolver discountResolver;

    @Before
    public void setUp() {

        this.orderRepository = new OrderRepositoryImpl();

        this.discountResolver = new FreeDrinkFrequentNumberDiscountResolver(orderRepository);
    }

    @Test
    public void testResolveZeroDiscountsForEmptyOrder() {

        var order = new Order();
        order.setUsername("myUsername");

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveZeroDiscountsForOrderWithFourDrinks() {

        var order = createOrder(4);

        var discounts = discountResolver.resolve(order);

        assertEquals(0, discounts.size());
    }

    @Test
    public void testResolveOneDiscountForWithoutPreviousOrders() {

        var order = createOrder(5);

        var discounts = discountResolver.resolve(order);

        assertEquals(1, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free 5th drink)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    @Test
    public void testResolveOneDiscountWithPreviousOrders() {

        orderRepository.save(createOrder(5));

        var order = createOrder(5);

        var discounts = discountResolver.resolve(order);

        assertEquals(1, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free 5th drink)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    @Test
    public void testResolveTwoDiscountWithPreviousOrders() {

        orderRepository.save(createOrder(3));

        var order = createOrder(7);

        var discounts = discountResolver.resolve(order);

        assertEquals(2, discounts.size());

        var discount = discounts.get(0);

        assertEquals("myComponent (Free 5th drink)", discount.getName());
        assertEquals(ONE.negate(), discount.getPrice());
    }

    //
    // private

    private Order createOrder(int numDrinks) {

        var order = new Order();
        order.setUsername("myUsername");

        IntStream.range(0, numDrinks).forEach(index ->
            order.getItems().add(component(DRINK))
        );

        order.getItems().add(component(SNACK));

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
