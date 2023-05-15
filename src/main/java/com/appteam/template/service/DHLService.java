package com.appteam.template.service;

import com.appteam.template.dto.OrderData;
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
import java.util.NoSuchElementException;

@Service
public class DHLService {
    @Autowired
    private OrderService orderService;

    private void sendDataFromResponse(HttpResponse<String> response) throws NoSuchElementException {
        JSONObject responseObject = new JSONObject(response.body());
        if (!responseObject.has("shipments")) {
            throw new NoSuchElementException("No orders found");
        }
        JSONArray orders = (JSONArray) responseObject.get("shipments");
        for (int i = 0; i < orders.length(); i++) {
            JSONObject order = orders.getJSONObject(i);
            Long id = order.getLong("id");
            String service = order.getString("service");
            String merchant = "?";
            String status = order.getJSONObject("status").toString();
            OrderData data = new OrderData(id, service, merchant, status);
            // push data to database
            orderService.saveOrder(data);
        }
    }

    public String updateShipmentInfo(String shipmentId) {
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
            sendDataFromResponse(response);
            return response.body();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @Scheduled(fixedRate = 100000)
    @Async
    public void updateAllShipmentsStatus() throws InterruptedException {
        System.err.println("updating shipment status...");
        Thread.sleep(100000);
    }
}