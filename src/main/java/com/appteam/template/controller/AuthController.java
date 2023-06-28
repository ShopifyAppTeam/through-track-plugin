package com.appteam.template.controller;

import com.appteam.template.data.User;
import com.appteam.template.dto.ShopData;
import com.appteam.template.dto.TokenData;
import com.appteam.template.service.ShopService;
import com.appteam.template.service.TokenService;
import com.appteam.template.service.UserService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    ShopService shopService;

    @Autowired
    TokenService tokenService;

    public String getEmailFromRequest(final HttpServletRequest request) {
        TokenData tokenData = getTokenDataFromRequest(request);
        if (tokenData == null) {
            return "";
        } else {
            return tokenData.getEmail();
        }
    }

    public TokenData getTokenDataFromRequest(final HttpServletRequest request) {
        TokenData tokenData = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie == null) {
                continue;
            }
            if (cookie.getName().startsWith("token")) {
                try {
                    JSONObject json = new JSONObject(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                    String tokenKey = json.getString("token");
                    String email = json.getString("email");
                    tokenData = tokenService.getTokenByKey(tokenKey);
                    if (tokenData != null && Objects.equals(tokenData.getEmail(), email)) {
                        break;
                    }
                } catch (UnsupportedEncodingException e) {
                    continue;
                }
            }
        }
        return tokenData;
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(final HttpServletRequest request) {
        TokenData tokenData = getTokenDataFromRequest(request);
        if (tokenData == null) {
            return new ResponseEntity<>(false, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(tokenService.deleteToken(tokenData.getKey()), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<String> isAutorized(final HttpServletRequest request) {
        TokenData tokenData = getTokenDataFromRequest(request);
        if (tokenData == null) {
            return new ResponseEntity<>("user is not autorized", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(tokenData.getEmail(), HttpStatus.OK);
        }
    }

    @GetMapping("/add_shop/{subdomain}/code={code}")
    public ResponseEntity<Boolean> addShop(final @PathVariable String subdomain, final @PathVariable String code,
                                           final HttpServletRequest request) throws URISyntaxException, IOException, InterruptedException {
        String email = getEmailFromRequest(request);
        if (email.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.METHOD_NOT_ALLOWED);
        } else {
            String token = getTokenFromShopify(code, subdomain);
            User user = userService.getUserRepository().getUserByEmail(email);
            if (user == null) {
                return new ResponseEntity<>(false, HttpStatus.METHOD_NOT_ALLOWED);
            }
            ShopData shopData = new ShopData(subdomain, token, user, new ArrayList<>());
            shopService.saveShop(shopData);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @GetMapping("/delete_shop/{subdomain}")
    public ResponseEntity<Boolean> deleteShop(final @PathVariable String subdomain, final HttpServletRequest request) {
        if (getEmailFromRequest(request).equals("")) {
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(shopService.deleteShop(subdomain), HttpStatus.OK);
    }

    private String getTokenFromShopify(String code, String subdomain) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://" + subdomain + "/admin/oauth/access_token?client_id=" + "{client id}" + "&client_secret=" + "{client secret}" + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url, String.class);
        return (new JSONObject(res)).getString("token");
    }
}
