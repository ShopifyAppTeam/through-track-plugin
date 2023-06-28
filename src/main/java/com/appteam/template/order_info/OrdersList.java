package com.appteam.template.order_info;

import com.appteam.template.dto.OrderData;
import com.shopify.ShopifySdk;
import com.shopify.model.*;

import java.util.ArrayList;

public class OrdersList {
    private final String token;
    private final String subdomain;
    final ShopifySdk shopifySdk;

    public OrdersList(String token, String subdomain) {
        this.token = token;
        this.subdomain = subdomain;
        shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
    }

    public ArrayList<OrderData> getOrdersDataList() {
        ArrayList<OrderData> orders = new ArrayList<>();
        ShopifyPage<ShopifyOrder> shopifyOrders = shopifySdk.getOrders();
        for (ShopifyOrder shopifyOrder : shopifyOrders) {
            orders.add(OrderInfo.getOrderDataFromShopifyOrder(shopifyOrder, shopifySdk));
        }
        return orders;
    }
}
