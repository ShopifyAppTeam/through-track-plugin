package com.appteam.template.controller;

import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

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
}