package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.appteam.template.order_info.OrderInfo;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyShippingLine;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderInfoTest {

    @Mock
    OrderInfo orderInfoMock = Mockito.mock(OrderInfo.class);
    JSONObject emptyOrderJson = new JSONObject();
    Order emptyOrder = new Order();

    {
        Mockito.when(orderInfoMock.getOrderJson()).thenReturn(emptyOrderJson);
        Mockito.when(orderInfoMock.getOrder()).thenReturn(emptyOrder);
    }

    @Test
    void getOrderJsonTest() {
        assertEquals(orderInfoMock.getOrderJson(), emptyOrderJson);
    }

    @Test
    void getOrderTest() {
        assertEquals(orderInfoMock.getOrder(), emptyOrder);
    }

    @Test
    void getOrderFromShopifyOrderTest() {
        ArrayList<ShopifyOrder> shopifyOrders = new ArrayList<ShopifyOrder>();
        for (int i = 0; i < 100; ++i) {
            ShopifyOrder e = new ShopifyOrder();
            e.setId(Long.toString((long) (Math.random() * ((100000000 - 10000) + 1)) + 10000));

            List<ShopifyShippingLine> shopifyShippingLines = new ArrayList<ShopifyShippingLine>();
            ShopifyShippingLine shopifyShippingLine = new ShopifyShippingLine();
            shopifyShippingLine.setId(Long.toString((long) (Math.random() * ((100000000 - 10000) + 1)) + 10000));
            shopifyShippingLines.add(shopifyShippingLine);
            e.setShippingLines(shopifyShippingLines);
            shopifyOrders.add(e);
        }
        for (ShopifyOrder shopifyOrder : shopifyOrders) {
            assertEquals(shopifyOrder.getId(), OrderInfo.getOrderFromShopifyOrder(shopifyOrder).getId().toString());
            assertEquals(shopifyOrder.getShippingLines().get(0).getId(), OrderInfo.getOrderFromShopifyOrder(shopifyOrder).getShipmentId().toString());
        }


    }


}