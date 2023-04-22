package com.appteam.template.controller;

import com.appteam.template.order_info.OrderInfo;
import com.appteam.template.order_info.OrdersList;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderInfoController {

    @GetMapping("/orders-list")
    public String OrdersList() {
        try {
            OrdersList ordersList = new OrdersList("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", "java-shop1");
            return ordersList.getOrdersDataList().toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @GetMapping("/order-information")
    public String orderInfo() {
        try {
            OrderInfo orderInfo= new OrderInfo("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", "java-shop1", "5282890514713");
            return orderInfo.getOrderData().toString();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }
}
