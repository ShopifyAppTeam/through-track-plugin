package com.appteam.template.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class OrdersListTest {
    @Mock
    OrdersList ordersListMock = Mockito.mock(OrdersList.class);
    {
        Mockito.when(ordersListMock.getOrders()).thenReturn("ordersList");
    }

    @Test
    void getOrders() {
        assertEquals("ordersList", ordersListMock.getOrders());
    }
}