package com.appteam.template.controller;

import com.appteam.template.dto.TokenData;
import com.appteam.template.oauth.CustomOAuth2User;
import com.appteam.template.service.DHLService;
import com.appteam.template.service.ShopService;
import com.appteam.template.service.UserService;
import com.shopify.ShopifySdk;
import com.shopify.model.ShopifyShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    ShopService shopService;

    private final DHLService DHLservice;

    private final AuthController authController = new AuthController();

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
    public ResponseEntity<String> myStoreInfo(final HttpServletRequest request) {
        String email = authController.getEmailFromRequest(request);
        if (email.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        String subdomain = userService.getUserByEmail(email).getCurrentShop();
        String token = shopService.getShopBySubdomain(subdomain).getToken();
//        String token = "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"; // API token, generated in store
//        String subdomain = "appteamtest";
        final ShopifySdk shopifySdk = ShopifySdk.newBuilder().withSubdomain(subdomain).withAccessToken(token).build();
        final ShopifyShop shopifyShop = shopifySdk.getShop();
        return new ResponseEntity<>(shopifyShop.getShop().getName(), HttpStatus.CREATED);
    }

    /**
     * Call to DHL API, that updates shipment status in database and returns it
     */
    @GetMapping("/my-shipment-status")
    public ResponseEntity<String> myShipmentStatus(@RequestParam Optional<String> id, final HttpServletRequest request) {
        if (authController.getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(DHLservice.getShipmentInfo(id.orElse(null)), HttpStatus.OK);
    }
}