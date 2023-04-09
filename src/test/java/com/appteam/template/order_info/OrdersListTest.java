package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.order_info.OrdersList;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrdersListTest {
    @Mock
    OrdersList ordersListMock = Mockito.mock(OrdersList.class);
    ArrayList<OrderData> emptyOrdersDataList = new ArrayList<OrderData>();
    {
        Mockito.when(ordersListMock.getOrdersDataList()).thenReturn(emptyOrdersDataList);
    }

    @Test
    void getOrdersData() {
        assertEquals(emptyOrdersDataList, ordersListMock.getOrdersDataList());
    }
}