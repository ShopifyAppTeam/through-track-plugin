package com.appteam.template.order_info;

import com.shopify.ShopifySdk;
import com.shopify.model.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

//@RestController
public class OrdersList {
    private final String token;  // API token, generated in store
    private final String subdomain;
    final ShopifySdk shopifySdk;

    OrdersList(String token, String subdomain) {
        this.token = token;
        this.subdomain = subdomain;
        shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
    }

    //@GetMapping("/store-orders")
    public String getOrders() {
        try {
            ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders();
            return shopifyOrders.toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }
}
