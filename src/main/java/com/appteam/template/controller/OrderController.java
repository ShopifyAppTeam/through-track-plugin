package com.appteam.template.controller;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.service.DefaultOrderService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource(name = "orderService")
    private DefaultOrderService orderService;

    @GetMapping
    public List<OrderData> allOrders() {
        return orderService.getAllOrders();
    }
    @PostMapping
    public OrderData addOrder(final @RequestBody OrderData data) {
        return orderService.saveOrder(data);
    }

    @GetMapping("/{id}")
    public OrderData getOrder(final @PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteOrder(final @PathVariable Long id) {
        return orderService.deleteOrder(id);

    }

}
