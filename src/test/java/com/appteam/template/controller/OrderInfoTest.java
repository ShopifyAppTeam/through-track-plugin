package com.appteam.template.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class OrderInfoTest {

    @Mock
    OrderInfo orderInfoMock = Mockito.mock(OrderInfo.class);
    JSONObject emptyOrderInfo = new JSONObject();

    {
       Mockito.when(orderInfoMock.getOrderInfo()).thenReturn(emptyOrderInfo);
    }
    @Test
    void getOrderInfo() {
        assertEquals(orderInfoMock.getOrderInfo(), emptyOrderInfo);
    }
}