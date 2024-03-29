package com.plainpixel.finalapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.util.Arrays;

public class OrderGroupTest {

    @Test
    public void testOrderGroupInitialization() {
        OrderGroup orderGroup = new OrderGroup("ABC123", "2024-03-28");

        assertNotNull(orderGroup);
        assertEquals("ABC123", orderGroup.getTranscationCode());
        assertEquals("2024-03-28", orderGroup.getOrderDate());
        assertEquals(0, orderGroup.getTotalOrderCost());
        assertEquals(0, orderGroup.getItemTotalCost().size());
    }

    @Test
    public void testAddItemTotalCost() {
        OrderGroup orderGroup = new OrderGroup("ABC123", "2024-03-28");
        orderGroup.addItemTotalCost(100);
        orderGroup.addItemTotalCost(200);

        assertEquals(2, orderGroup.getItemTotalCost().size());
        assertEquals(Arrays.asList(100, 200), orderGroup.getItemTotalCost());
        assertEquals(300, orderGroup.getTotalOrderCost());
    }

    @Test
    public void testGetTotalOrderCostWithNoItems() {
        OrderGroup orderGroup = new OrderGroup("ABC123", "2024-03-28");

        assertEquals(0, orderGroup.getTotalOrderCost());
    }

    @Test
    public void testGetTotalOrderCostWithItems() {
        OrderGroup orderGroup = new OrderGroup("ABC123", "2024-03-28");
        orderGroup.addItemTotalCost(150);
        orderGroup.addItemTotalCost(250);

        assertEquals(400, orderGroup.getTotalOrderCost());
    }
}
