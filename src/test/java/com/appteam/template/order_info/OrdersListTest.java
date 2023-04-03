package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.appteam.template.order_info.OrdersList;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrdersListTest {
    @Mock
    OrdersList ordersListMock = Mockito.mock(OrdersList.class);
    ArrayList<Order> emptyOrdersList = new ArrayList<Order>();
    {
        Mockito.when(ordersListMock.getOrdersList()).thenReturn(emptyOrdersList);
    }

    @Test
    void getOrders() {
        assertEquals(emptyOrdersList, ordersListMock.getOrdersList());
    }
}