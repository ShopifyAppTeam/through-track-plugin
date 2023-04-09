package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyShippingLine;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class OrderInfo {
    private final String token; // API token, generated in store
    private final String subdomain;
    private final String orderId;
    final ShopifySdk shopifySdk;

   OrderInfo(String token, String subdomain, String orderId) {
        this.token = token;
        this.subdomain = subdomain;
        shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
        this.orderId = orderId;
    }

   public JSONObject getOrderDataJson() {
        ShopifyOrder order = shopifySdk.getOrder(orderId);
        JSONObject orderInfoJson = new JSONObject();
        try {
            orderInfoJson.put("Id", order.getId());
            orderInfoJson.put("Status-url", order.getOrderStatusUrl());
            orderInfoJson.put("Link", order.getLandingSite());  // not sure if this is the right field
            orderInfoJson.put("Tracking-info", order.getFulfillments().toString());

            for (ShopifyShippingLine line : order.getShippingLines()) {
                orderInfoJson.append("Shipping-method", line.getSource());
            }
            for (ShopifyShippingLine line : order.getShippingLines()) {
                orderInfoJson.append("Shipment-id", line.getId());
            }
            return orderInfoJson;
        } catch (Exception exc) {
           orderInfoJson.put("Exception", exc.getMessage());
           return orderInfoJson;
        }
    }

    public static OrderData getOrderDataFromShopifyOrder(ShopifyOrder shopifyOrder) {
        OrderData orderData = new OrderData();
        orderData.setId(Long.valueOf(shopifyOrder.getId()));
        orderData.setShipmentId(Long.valueOf(shopifyOrder.getShippingLines().get(0).getId()));
        return orderData;
    }

    public OrderData getOrderData() {
        ShopifyOrder shopifyOrder = shopifySdk.getOrder(orderId);
        return getOrderDataFromShopifyOrder(shopifyOrder);
    }
}
