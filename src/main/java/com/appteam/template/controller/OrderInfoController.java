package com.appteam.template.controller;

import com.appteam.template.dto.OrderData;
import com.appteam.template.order_info.OrderInfo;
import com.appteam.template.order_info.OrdersList;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/order-info")
public class OrderInfoController {

    @GetMapping("/orders-list/{subdomain}/{token}")
    public ResponseEntity<ArrayList<OrderData>> ordersList(final @PathVariable String token, final @PathVariable String subdomain) {
        OrdersList ordersList = new OrdersList(token, subdomain);
        return new ResponseEntity<>(ordersList.getOrdersDataList(), new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/order-information/{subdomain}/{token}/{id}")
    public ResponseEntity<OrderData> orderInfo(final @PathVariable String token, final @PathVariable String subdomain, final @PathVariable String id) {

        OrderInfo orderInfo = new OrderInfo(token, subdomain, id);
        return new ResponseEntity<>(orderInfo.getOrderData(), new HttpHeaders(), HttpStatus.CREATED);
    }
}
