package com.appteam.template.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DHLService {
    public String getShipmentInfo(String shipmentId) {
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
            String shipmentInfo = response.body();

            return shipmentInfo;
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    @Scheduled(fixedRate = 10000)
    @Async
    public void updateAllShipmentsStatus() throws InterruptedException {
        System.err.println("updating shipment status...");
        Thread.sleep(10000);
    }
}
