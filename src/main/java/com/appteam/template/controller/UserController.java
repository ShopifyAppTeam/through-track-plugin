package com.appteam.template.controller;

import com.appteam.template.data.Order;
import com.appteam.template.data.Shop;
import com.appteam.template.dto.OrderData;
import com.appteam.template.dto.UserData;
import com.appteam.template.service.DefaultOrderService;
import com.appteam.template.service.OrderService;
import com.appteam.template.service.UserService;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Resource(name = "orderService")
    private DefaultOrderService orderService;

    @Autowired
    private AuthController authController;

    @GetMapping
    public ResponseEntity<List<UserData>> allUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserData> addUser(final @RequestBody UserData data) {
        return new ResponseEntity<>(userService.saveUser(data), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserData> getUser(final @PathVariable String email, final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<String>> getUserShops(final HttpServletRequest request) {
        String email = authController.getEmailFromRequest(request);
        if(email.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        UserData userData = userService.getUserByEmail(email);
        if(userData == null) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        Collection<Shop> shops = userData.getShops();
        return new ResponseEntity<>(shops.stream().map(Shop::getSubdomain).collect(Collectors.toList()), HttpStatus.OK);
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(final @PathVariable String email, final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(userService.deleteUser(email), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<String>> getUserOrders(final HttpServletRequest request) {
        String email = authController.getEmailFromRequest(request);
        if(email.equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        UserData userData = userService.getUserByEmail(email);
        if(userData == null) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        Collection<Shop> shops = userData.getShops();
        //Collection<Order> orders = new ArrayList<>();
        List<String> jsonArray = new ArrayList<>();
        List<OrderData> orders = orderService.getUserOrders(email);

        for (var order : orders) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", order.getId());
            jsonObject.put("shop", "appteamtest");
            jsonObject.put("service", order.getService());
            JSONObject jsob = new JSONObject(order.getStatus());
            System.err.println(order.getStatus());
            jsonObject.put("status", jsob.get("status"));
            jsonArray.add(jsonObject.toString());
        }

        return new ResponseEntity<>(jsonArray, HttpStatus.OK);

//        int i = 0;
//        for (Shop shop : shops) {
//            for (Order order : shop.getOrders()) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.append("id", order.getId());
//                jsonObject.append("shop", shop.getSubdomain());
//                jsonObject.append("service", order.getService());
//                jsonObject.append("status", new JSONObject(order.getStatus()).get("status"));
//                jsonArray.add(jsonObject);
//                i++;
//            }
//        }
//        return new ResponseEntity<>(jsonArray, HttpStatus.OK);
    }
}
