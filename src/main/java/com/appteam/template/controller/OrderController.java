package com.appteam.template.controller;

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
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource(name = "orderService")
    private DefaultOrderService orderService;

    @Resource(name = "emailService")
    private EmailService emailService;

    private final AuthController authController = new AuthController();

    @GetMapping
    public ResponseEntity<List<OrderData>> allOrders() {

        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderData> addOrder(final @RequestBody OrderData data) {
        return new ResponseEntity<>(orderService.saveOrder(data), new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderData> getOrder(final @PathVariable Long id, final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(final @PathVariable Long id, final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(orderService.deleteOrder(id), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderData>> getOrdersByStatusAndMerchant(final @RequestParam String merchant,
                                                                        final @RequestParam String status,
                                                                        final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(orderService.getUserOrdersByStatus(merchant, status), HttpStatus.OK);
    }

    @GetMapping("/service")
    public ResponseEntity<List<OrderData>> getOrdersByServicesAndMerchant(final @RequestParam String merchant,
                                                                          final @RequestParam Collection<String> services) {
        return new ResponseEntity<>(orderService.getUserOrdersByServices(merchant, services), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderData>> getUserOrders(final @RequestParam String user) {
        return new ResponseEntity<>(orderService.getUserOrders(user), HttpStatus.OK);
    }
}
