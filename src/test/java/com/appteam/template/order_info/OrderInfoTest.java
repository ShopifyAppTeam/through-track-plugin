package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.order_info.OrderInfo;
import com.shopify.ShopifySdk;
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
    JSONObject emptyOrderDataJson = new JSONObject();
    OrderData emptyOrderData = new OrderData();

    {
        Mockito.when(orderInfoMock.getOrderDataJson()).thenReturn(emptyOrderDataJson);
        Mockito.when(orderInfoMock.getOrderData()).thenReturn(emptyOrderData);
    }

    @Test
    void getOrderJsonTest() {
        assertEquals(orderInfoMock.getOrderDataJson(), emptyOrderDataJson);
    }

    @Test
    void getOrderTest() {
        assertEquals(orderInfoMock.getOrderData(), emptyOrderData);
    }

    @Test
    void getOrderFromShopifyOrderTest() {
        ArrayList<ShopifyOrder> shopifyOrders = new ArrayList<ShopifyOrder>();
        ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain("").withAccessToken("").build();
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
            assertEquals(shopifyOrder.getId(), OrderInfo.getOrderDataFromShopifyOrder(shopifyOrder, shopifySdk).getId().toString());
        }
    }
}