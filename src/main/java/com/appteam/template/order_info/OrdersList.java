package com.appteam.template.order_info;

import com.appteam.template.data.Order;
import com.shopify.ShopifySdk;
import com.shopify.model.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Order> getOrdersList() {
        ArrayList<Order> orders = new ArrayList<Order>();
        ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders();
        for (ShopifyOrder shopifyOrder : shopifyOrders) {
            orders.add(OrderInfo.getOrderFromShopifyOrder(shopifyOrder));
        }
        return orders;
    }
}
