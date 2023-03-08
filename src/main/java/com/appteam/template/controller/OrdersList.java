package com.appteam.template.controller;

import com.shopify.ShopifySdk;
import com.shopify.model.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
public class OrdersList {
    private final String token = ""; // API token, generated in store
    private final String subdomain = "";
    final ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();

    @GetMapping("/store-orders")
    public String getOrders() {
        try {
            ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders();
            return shopifyOrders.toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/id")
    public String getOrderId(ShopifyOrder shopifyOrder) {
        try {
            return shopifyOrder.getId();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/shipment-id")
    public String getOrderShipmentId(ShopifyOrder shopifyOrder) {
        try {
            StringBuilder shopifyOrderShipmentId = new StringBuilder();
            for (ShopifyShippingLine line : shopifyOrder.getShippingLines()) {
                shopifyOrderShipmentId.append(line.getId());
                shopifyOrderShipmentId.append('\n');
            }

            return shopifyOrderShipmentId.toString(); // shipment id
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/shipping-method")
    public String getOrderShippingMethod(ShopifyOrder shopifyOrder) {
        try {
            StringBuilder shopifyOrderShippingMethod = new StringBuilder();
            for (ShopifyShippingLine line : shopifyOrder.getShippingLines()) {
                shopifyOrderShippingMethod.append(line.getSource());
                shopifyOrderShippingMethod.append('\n');
            }
            return shopifyOrderShippingMethod.toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/order-status")
    public String getOrderStatus(ShopifyOrder shopifyOrder) {
        try {
            return shopifyOrder.getOrderStatusUrl();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/order-link")
    public String getOrderLink(ShopifyOrder shopifyOrder) {
        try {
            return shopifyOrder.getLandingSite();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/store-order-parameters/tracking-info")
    public String getOrderTrackingInfo(ShopifyOrder shopifyOrder) {
        try {
            List<ShopifyFulfillment> shopifyOrderTrackingInfo = shopifyOrder.getFulfillments();
            return shopifyOrderTrackingInfo.toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

}
