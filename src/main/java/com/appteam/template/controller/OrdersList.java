package com.appteam.template.controller;

import com.shopify.ShopifySdk;
import com.shopify.model.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// @RestController
public class OrdersList {
    private final String token;  // API token, generated in store
    private final String subdomain;
    final ShopifySdk shopifySdk;

    OrdersList(String token_, String subdomain_) {
        token = token_;  // = "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";
        subdomain = subdomain_;  // = "java-shop1";
        shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
    }

    // @GetMapping("/store-orders")
    public String getOrders() {
        try {
            ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders();
            return shopifyOrders.toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }
}
