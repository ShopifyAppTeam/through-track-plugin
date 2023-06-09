package com.appteam.template.controller;

import com.appteam.template.service.DHLService;
import com.appteam.template.service.UserService;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RestController
public class SampleController {
    private final DHLService DHLservice;

    public SampleController(DHLService DHLservice) {
        this.DHLservice = DHLservice;
    }

    @GetMapping("/")
    public ResponseEntity<String> greetings() {
        return new ResponseEntity<>("Greetings from Application!\n", HttpStatus.CREATED);
    }

    /**
     * Sample call to Shopify API
     */

    @GetMapping("/my-store-info")
    public ResponseEntity<String> myStoreInfo() {
        String token = "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"; // API token, generated in store
        String subdomain = "appteamtest";
        final ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
        final ShopifyShop shopifyShop = shopifySdk.getShop();
        return new ResponseEntity<>(shopifyShop.getShop().getName(), HttpStatus.CREATED);
    }

    /**
     * Call to DHL API, that updates shipment status in database and returns it
     */
    @GetMapping("/my-shipment-status")
    public ResponseEntity<String> myShipmentStatus(@RequestParam Optional<String> id) {
        return new ResponseEntity<>(DHLservice.getShipmentInfo(id.orElse(null)), HttpStatus.OK);
    }
}