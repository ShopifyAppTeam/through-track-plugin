package com.appteam.template.controller;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.service.DefaultOrderService;
import com.appteam.template.service.EmailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource(name = "orderService")
    private DefaultOrderService orderService;

    @Resource(name = "emailService")
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<OrderData>> allOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderData> addOrder(final @RequestBody OrderData data) {
        return new ResponseEntity<>(orderService.saveOrder(data), new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderData> getOrder(final @PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteOrder(final @PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/status")
    public List<OrderData> getOrdersByStatusAndMerchant(final @RequestParam String merchant,
                                                        final @RequestParam String status) {
        return orderService.getUserOrdersByStatus(merchant, status);
    }
}
