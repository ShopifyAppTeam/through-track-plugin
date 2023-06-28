package com.appteam.template.controller;

import com.appteam.template.dto.OrderData;
import com.appteam.template.dto.ShopData;
import com.appteam.template.order_info.OrderInfo;
import com.appteam.template.order_info.OrdersList;
import com.appteam.template.service.ShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/order-info")
public class OrderInfoController {

    @Autowired
    AuthController authController;

    @Autowired
    ShopService shopService;

    @GetMapping("/orders-list/{subdomain}")
    public ResponseEntity<ArrayList<OrderData>> ordersList(final HttpServletRequest request, final @PathVariable String subdomain) {
        String email = authController.getEmailFromRequest(request);
        if (email.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        ShopData shopData = shopService.getShopBySubdomain(subdomain);
        if (shopData == null) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        OrdersList ordersList = new OrdersList(shopData.getToken(), subdomain);
        return new ResponseEntity<>(ordersList.getOrdersDataList(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/order-information/{subdomain}/{id}")
    public ResponseEntity<OrderData> orderInfo(final HttpServletRequest request, final @PathVariable String subdomain, final @PathVariable String id) {
        String email = authController.getEmailFromRequest(request);
        if (email.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        ShopData shopData = shopService.getShopBySubdomain(subdomain);
        if (shopData == null) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        OrderInfo orderInfo = new OrderInfo(shopData.getToken(), subdomain, id);
        return new ResponseEntity<>(orderInfo.getOrderData(), new HttpHeaders(), HttpStatus.OK);
    }
}
