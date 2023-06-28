package com.appteam.template.service;

import com.appteam.template.dto.OrderData;
import com.appteam.template.dto.UserData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DHLService {
    HashMap<String, Timer> updateTimers = new HashMap<>();
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private void sendDataFromResponse(HttpResponse<String> response, String email) throws NoSuchElementException {
        JSONObject responseObject = new JSONObject(response.body());
        if (!responseObject.has("shipments")) {
            throw new NoSuchElementException("No orders found");
        }
        JSONArray orders = (JSONArray) responseObject.get("shipments");
        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);
            Long id = order.getLong("id");
            String service = order.getString("service");
            String status = order.getJSONObject("status").toString();
            OrderData data = new OrderData(id, service, email, status, null);
            // push data to database
            orderService.saveOrder(data);
        }
    }

    private void setOrderAsNotFound(String shipmentId, String email) {
        OrderData data = new OrderData(Long.parseLong(shipmentId), "service", email, "Order not found", null);
        orderService.saveOrder(data);
    }

    public String updateShipmentInfo(String shipmentId, String email) {
        try {
            if (shipmentId == null) {
                return "No shipment tracking number";
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://api-eu.dhl.com/track/shipments?trackingNumber=" + shipmentId))
                    .header("DHL-API-Key", "14REm4bzMWj4sZUNvM950izekwaYUAVN")
                    .method("GET", java.net.http.HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            sendDataFromResponse(response, email);
            return response.body();
        } catch (Exception exc) {
            setOrderAsNotFound(shipmentId, email);
            return exc.getMessage();
        }
    }

    public void updateAllShipmentsStatus() {
        System.err.println("updating all shipment status...");
        for (UserData user : userService.getAllUsers()) {
            setUserUpdateTimer(user);
        }
    }

    public void setUserUpdateTimer(UserData user) {
        System.err.println("updating user update time...");
        String email = user.getEmail();
        if (updateTimers.get(email) != null) {
            updateTimers.get(email).cancel();
        }
        updateTimers.put(email, new Timer());
        updateTimers.get(email).schedule(new TimerTask() {
            public void run() {
                updateUserShipments(user);
                System.err.println("updating user " + email + " status...");
            }
        }, 0, user.getUpdateTime());
    }

    private void updateUserShipments(UserData user) {
        List<OrderData> userOrders = orderService.getAllOrders().stream()
                .filter(order -> user.getEmail().equals(order.getMerchant())).collect(Collectors.toList());
        for (OrderData order : userOrders) {
            updateShipmentInfo(order.getId().toString(), user.getEmail());
        }
    }
}