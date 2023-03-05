package com.appteam.template.controller;

import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

@RestController
public class SampleController {
    @GetMapping("/")
    public String greetings() {
        return "Greetings from Application!\n";
    }


    /*
        Sample call to Shopify API
    */
    @GetMapping("/my-store-info")
    public String myStoreInfo() {
        try {
            String token = "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"; // API token, generated in store
            String subdomain = "appteamtest";
            final ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
            final ShopifyShop shopifyShop = shopifySdk.getShop();
            return shopifyShop.getShop().getName();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }

    /*
        Sample call to DHL API
    */
    @GetMapping("/my-shipment-status")
    public String myShipmentStatus(@RequestParam String id) {
        try {
            if (id == null) {
                return "No shipment tracking number";
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://api-eu.dhl.com/track/shipments?trackingNumber=" + id))
                    .header("DHL-API-Key", "14REm4bzMWj4sZUNvM950izekwaYUAVN")
                    .method("GET", java.net.http.HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception exc) {
            return exc.getMessage();
        }
    }
}