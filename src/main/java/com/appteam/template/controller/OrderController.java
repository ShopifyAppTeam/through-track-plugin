package com.appteam.template.controller;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.service.DefaultOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource(name = "orderService")
    private DefaultOrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderData>> allOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<OrderData> addOrder(final @RequestBody OrderData data) {
        return new ResponseEntity<>(orderService.saveOrder(data), new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderData> getOrder(final @PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(final @PathVariable Long id) {
        return new ResponseEntity<>(orderService.deleteOrder(id), new HttpHeaders(), HttpStatus.CREATED);
    }
}
