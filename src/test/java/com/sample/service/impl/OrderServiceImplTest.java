package com.sample.service.impl;

import static com.sample.view.ProductCoffee.CoffeeExtraType.FOAMED_MILK;
import static com.sample.view.ProductCoffee.CoffeeSizeType.LARGE;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.sample.factory.OrderServiceFactory;
import com.sample.service.OrderService;
import com.sample.view.OrderRequest;
import com.sample.view.ProductBaconRoll;
import com.sample.view.ProductCoffee.ProductCoffeeBuilder;
import com.sample.view.ProductOrangeJuice;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;

/**
 * Unit testing for {@link OrderServiceImpl}
 */
public class OrderServiceImplTest {

    private final OrderService orderService = OrderServiceFactory.createOrderService();

    @Test
    public void testCreateEmptyOrder() {

        var request = new OrderRequest("myUsername", emptyList());

        var response = orderService.createOrder(request);

        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(0, response.getItems().size());
        assertEquals(0, response.getDiscounts().size());
        assertEquals(ZERO, response.getTotalPrice());
    }

    @Test
    public void testCreateOrderWithJuiceAndLargeCoffeeWithFoamedMilk() {

        var request = new OrderRequest("myUsername", List.of(
            new ProductOrangeJuice(),
            new ProductCoffeeBuilder(LARGE)
                .addExtra(FOAMED_MILK)
                .build()
        ));

        var response = orderService.createOrder(request);

        assertNotNull(response.getId());
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(3, response.getItems().size());

        assertEquals("Freshly squeezed orange juice", response.getItems().get(0).getName());
        assertEquals(BigDecimal.valueOf(3.95), response.getItems().get(0).getPrice());

        assertEquals("Coffee large", response.getItems().get(1).getName());
        assertEquals(BigDecimal.valueOf(3.50), response.getItems().get(1).getPrice());

        assertEquals("Foamed milk", response.getItems().get(2).getName());
        assertEquals(BigDecimal.valueOf(0.50), response.getItems().get(2).getPrice());

        assertEquals(0, response.getDiscounts().size());
        assertEquals(BigDecimal.valueOf(7.95), response.getTotalPrice());
    }

    @Test
    public void testCreateOrderWithBaconRollAndLargeCoffeeWithFoamedMilk() {

        var request = new OrderRequest("myUsername", List.of(
            new ProductBaconRoll(),
            new ProductCoffeeBuilder(LARGE)
                .addExtra(FOAMED_MILK)
                .build()
        ));

        var response = orderService.createOrder(request);

        assertNotNull(response.getId());
        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(3, response.getItems().size());

        assertEquals("Bacon Roll", response.getItems().get(0).getName());
        assertEquals(BigDecimal.valueOf(4.50), response.getItems().get(0).getPrice());

        assertEquals("Coffee large", response.getItems().get(1).getName());
        assertEquals(BigDecimal.valueOf(3.50), response.getItems().get(1).getPrice());

        assertEquals("Foamed milk", response.getItems().get(2).getName());
        assertEquals(BigDecimal.valueOf(0.50), response.getItems().get(2).getPrice());

        assertEquals(1, response.getDiscounts().size());

        assertEquals("Foamed milk (Free extra)", response.getDiscounts().get(0).getName());
        assertEquals(BigDecimal.valueOf(-0.50), response.getDiscounts().get(0).getPrice());

        assertEquals(BigDecimal.valueOf(8.00), response.getTotalPrice());
    }
}
