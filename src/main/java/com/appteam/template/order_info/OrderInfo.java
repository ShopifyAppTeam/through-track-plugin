package com.appteam.template.order_info;

import com.appteam.template.dto.OrderData;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyOrder;
import com.shopify.model.ShopifyShippingLine;
import org.json.JSONObject;

public class OrderInfo {
    private final String token;
    private final String subdomain;
    private final String orderId;
    final ShopifySdk shopifySdk;

   public OrderInfo(String token, String subdomain, String orderId) {
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
            orderInfoJson.put("Link", order.getLandingSite());
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

    public static OrderData getOrderDataFromShopifyOrder(ShopifyOrder shopifyOrder, ShopifySdk shopifySdk) {
        OrderData orderData = new OrderData();
        orderData.setId(Long.valueOf(shopifyOrder.getId()));
        orderData.setMerchant(shopifySdk.getShop().getShop().getName());
        return orderData;
    }

    public OrderData getOrderData() {
        ShopifyOrder shopifyOrder = shopifySdk.getOrder(orderId);
        return getOrderDataFromShopifyOrder(shopifyOrder, shopifySdk);
    }
}
